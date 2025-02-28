package ru.mas.ktane_bot.handlers.solvers;

import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;
import ru.mas.ktane_bot.model.PortType;

import java.util.List;

public class CompWiresSolver extends Handler {

    private final static String DONT_CUT = "Не резать";
    private final static String CUT = "Резать";

    public CompWiresSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        if (message.equals("стоп")){
            userDataCache.solveModule(userId);
            return "Решено";
        }
        var bomb = userDataCache.getUserBomb(userId);
        var conditions = message.chars().mapToObj(c -> (char) c).toList();
        if (conditions.size() == 4) {
            return DONT_CUT;
        } else if (conditions.size() == 3) {
            if (!conditions.contains('s'))
                return bomb.isLastDigit(false) ? CUT : DONT_CUT;
            else if (!conditions.contains('b'))
                return bomb.getBatteriesCount() > 1 ? CUT : DONT_CUT;
            else
                return bomb.getPorts().contains(PortType.PARALLEL) ? CUT : DONT_CUT;
        } else if (conditions.size() == 2) {
            if (conditions.containsAll(List.of('l', 'b')))
                return bomb.getPorts().contains(PortType.PARALLEL) ? CUT : DONT_CUT;
            else if (conditions.containsAll(List.of('r', 'b')))
                return bomb.isLastDigit(false) ? CUT : DONT_CUT;
            else if (conditions.containsAll(List.of('s', 'r')))
                return CUT;
            else if (conditions.containsAll(List.of('s', 'b')))
                return DONT_CUT;
            else
                return bomb.getBatteriesCount() > 1 ? CUT : DONT_CUT;
        } else {
            if (conditions.contains('_') || conditions.contains('s'))
                return CUT;
            else if (conditions.contains('l'))
                return DONT_CUT;
            else
                return bomb.isLastDigit(false) ? CUT : DONT_CUT;
        }
    }
}
