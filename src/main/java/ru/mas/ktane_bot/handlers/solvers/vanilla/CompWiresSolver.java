package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.PortType;

import java.util.List;

import static ru.mas.ktane_bot.model.modules.CommonValues.CUT;
import static ru.mas.ktane_bot.model.modules.CommonValues.DONT_CUT;

@Component("compWiresSolver")
@RequiredArgsConstructor
public class CompWiresSolver implements Solver {

    private final static String DONT_CUT = "Не резать";
    private final static String CUT = "Резать";

    private final DataCache dataCache;

    @Override
    public String solve(String message, Long userId) {
        if (message.equals("stop")){
            dataCache.solveModule(userId);
            return "Решено";
        }
        var bomb = dataCache.getUserBomb(userId);
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
