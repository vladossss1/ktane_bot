package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.WireSeqModule;

import java.util.List;
import java.util.StringJoiner;

import static ru.mas.ktane_bot.model.CommonValues.CUT;
import static ru.mas.ktane_bot.model.CommonValues.DONT_CUT;

@Component("wireSeqSolver")
@RequiredArgsConstructor
public class WireSeqSolver implements Solver {

    private final DataCache dataCache;
    @Override
    public MessageDto solve(String message, String userId) {
        var module = (WireSeqModule) dataCache.getUserModule(userId);
        var result = new StringJoiner(" ");

        if (message.equals("_"))
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();

        for (int i = 0; i < message.length(); i += 2) {
            result.add(module.calculateCut(message.charAt(i), message.charAt(i + 1)));
        }

        if (module.getStage() == 3)
            dataCache.solveModule(userId);
        else
            module.nextStage();

        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result.toString()).build();
    }
}
