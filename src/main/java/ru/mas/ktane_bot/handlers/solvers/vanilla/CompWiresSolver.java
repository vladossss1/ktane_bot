package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.bomb.PortType;

import java.util.List;
import java.util.StringJoiner;

import static ru.mas.ktane_bot.model.CommonValues.CUT;
import static ru.mas.ktane_bot.model.CommonValues.DONT_CUT;

@Component("compWiresSolver")
@RequiredArgsConstructor
public class CompWiresSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        var wires = message.split(" ");
        var result = new StringJoiner("\n");
        for (var wire : wires) {
            var conditions = wire.chars().mapToObj(c -> (char) c).toList();
            if (conditions.size() == 4) {
                result.add(DONT_CUT);
            } else if (conditions.size() == 3) {
                if (!conditions.contains('s'))
                    result.add(bomb.isLastDigitOfSerialNumberEven() ? CUT : DONT_CUT);
                else if (!conditions.contains('b'))
                    result.add(bomb.getBatteriesCount() > 1 ? CUT : DONT_CUT);
                else
                    result.add(bomb.hasPort(PortType.PARALLEL) ? CUT : DONT_CUT);
            } else if (conditions.size() == 2) {
                if (conditions.containsAll(List.of('l', 'b')))
                    result.add(bomb.hasPort(PortType.PARALLEL) ? CUT : DONT_CUT);
                else if (conditions.containsAll(List.of('r', 'b')))
                    result.add(bomb.isLastDigitOfSerialNumberEven() ? CUT : DONT_CUT);
                else if (conditions.containsAll(List.of('s', 'r')))
                    result.add(CUT);
                else if (conditions.containsAll(List.of('s', 'b')))
                    result.add(DONT_CUT);
                else
                    result.add(bomb.getBatteriesCount() > 1 ? CUT : DONT_CUT);
            } else {
                if (conditions.contains('_') || conditions.contains('s'))
                    result.add(CUT);
                else if (conditions.contains('l'))
                    result.add(DONT_CUT);
                else
                    result.add(bomb.isLastDigitOfSerialNumberEven() ? CUT : DONT_CUT);
            }
        }
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result.toString()).build();
    }
}
