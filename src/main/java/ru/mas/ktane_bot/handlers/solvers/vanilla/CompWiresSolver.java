package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.PortType;

import java.util.List;

import static ru.mas.ktane_bot.model.CommonValues.CUT;
import static ru.mas.ktane_bot.model.CommonValues.DONT_CUT;

@Component("compWiresSolver")
@RequiredArgsConstructor
public class CompWiresSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        if (message.equals("stop")){
            dataCache.solveModule(userId);
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }
        var bomb = dataCache.getUserBomb(userId);
        var conditions = message.chars().mapToObj(c -> (char) c).toList();
        if (conditions.size() == 4) {
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(DONT_CUT).build();
        } else if (conditions.size() == 3) {
            if (!conditions.contains('s'))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(bomb.isLastDigit(false) ? CUT : DONT_CUT).build();
            else if (!conditions.contains('b'))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(bomb.getBatteriesCount() > 1 ? CUT : DONT_CUT).build();
            else
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(bomb.getPorts().contains(PortType.PARALLEL) ? CUT : DONT_CUT).build();
        } else if (conditions.size() == 2) {
            if (conditions.containsAll(List.of('l', 'b')))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(bomb.getPorts().contains(PortType.PARALLEL) ? CUT : DONT_CUT).build();
            else if (conditions.containsAll(List.of('r', 'b')))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(bomb.isLastDigit(false) ? CUT : DONT_CUT).build();
            else if (conditions.containsAll(List.of('s', 'r')))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(CUT).build();
            else if (conditions.containsAll(List.of('s', 'b')))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(DONT_CUT).build();
            else
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(bomb.getBatteriesCount() > 1 ? CUT : DONT_CUT).build();
        } else {
            if (conditions.contains('_') || conditions.contains('s'))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(CUT).build();
            else if (conditions.contains('l'))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(DONT_CUT).build();
            else
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(bomb.isLastDigit(false) ? CUT : DONT_CUT).build();
        }
    }
}
