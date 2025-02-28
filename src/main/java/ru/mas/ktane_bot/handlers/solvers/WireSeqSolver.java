package ru.mas.ktane_bot.handlers.solvers;

import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;
import ru.mas.ktane_bot.model.modules.WireSeq;

import java.util.List;
import java.util.Map;

public class WireSeqSolver extends Handler {

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

    private final static String DONT_CUT = "Не резать";
    private final static String CUT = "Резать";

    public WireSeqSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var module = (WireSeq) userDataCache.getUserModule(userId);
        if (message.equals("stop")){
            userDataCache.solveModule(userId);
            return "Решено";
        }
        if (message.length() == 2)
            return calculateCut(message.charAt(0), message.charAt(1), module);
         else if (message.length() == 4)
            return calculateCut(message.charAt(0), message.charAt(1), module) + " "
                    + calculateCut(message.charAt(2), message.charAt(3), module);
        else if (message.length() == 6)
            return calculateCut(message.charAt(0), message.charAt(1), module) + " "
                    + calculateCut(message.charAt(2), message.charAt(3), module) + " "
                    + calculateCut(message.charAt(4), message.charAt(5), module);

        return null;
    }

    private String calculateCut(char color, char letter, WireSeq module) {
        return switch (color) {
            case 'r' -> red.get(module.getRedCount()).contains(letter) ? CUT : DONT_CUT;
            case 'b' -> blue.get(module.getBlueCount()).contains(letter) ? CUT : DONT_CUT;
            default -> black.get(module.getBlackCount()).contains(letter) ? CUT : DONT_CUT;
        };
    }
}
