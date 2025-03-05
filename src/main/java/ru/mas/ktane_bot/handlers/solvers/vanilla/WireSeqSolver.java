package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.WireSeqModule;

import java.util.List;

import static ru.mas.ktane_bot.model.CommonValues.CUT;
import static ru.mas.ktane_bot.model.CommonValues.DONT_CUT;

@Component("wireSeqSolver")
@RequiredArgsConstructor
public class WireSeqSolver implements Solver {

    private final DataCache dataCache;

    private static final List<List<Character>> red = List.of(
            List.of('c'),
            List.of('b'),
            List.of('a'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('a', 'c'),
            List.of('a', 'b', 'c'),
            List.of('a', 'b'),
            List.of('b')
    );

    private static final List<List<Character>> blue = List.of(
            List.of('b'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('a'),
            List.of('b'),
            List.of('b', 'c'),
            List.of('c'),
            List.of('a', 'c'),
            List.of('a')
    );

    private static final List<List<Character>> black = List.of(
            List.of('a', 'b', 'c'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('b', 'c'),
            List.of('a', 'b'),
            List.of('c'),
            List.of('c')
    );

    @Override
    public MessageDto solve(String message, String userId) {
        var module = (WireSeqModule) dataCache.getUserModule(userId);
        String result;
        if (message.length() == 2)
            result = calculateCut(message.charAt(0), message.charAt(1), module);
        else if (message.length() == 4)
            result = calculateCut(message.charAt(0), message.charAt(1), module) + " "
                    + calculateCut(message.charAt(2), message.charAt(3), module);
        else if (message.length() == 6)
            result = calculateCut(message.charAt(0), message.charAt(1), module) + " "
                    + calculateCut(message.charAt(2), message.charAt(3), module) + " "
                    + calculateCut(message.charAt(4), message.charAt(5), module);
        else
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();

        if (module.getStage() == 3)
            dataCache.solveModule(userId);

        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }

    private String calculateCut(char color, char letter, WireSeqModule module) {
        return switch (color) {
            case 'r' -> red.get(module.getRedCount()).contains(letter) ? CUT : DONT_CUT;
            case 'b' -> blue.get(module.getBlueCount()).contains(letter) ? CUT : DONT_CUT;
            default -> black.get(module.getBlackCount()).contains(letter) ? CUT : DONT_CUT;
        };
    }
}
