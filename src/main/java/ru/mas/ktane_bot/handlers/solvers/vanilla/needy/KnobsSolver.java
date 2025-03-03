package ru.mas.ktane_bot.handlers.solvers.vanilla.needy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.handlers.solvers.Solver;

import java.util.Arrays;
import java.util.Map;

@Component("knobsSolver")
@RequiredArgsConstructor
public class KnobsSolver implements Solver {

    private static final String UP = "Вверх";
    private static final String DOWN = "Вниз";
    private static final String LEFT = "Влево";
    private static final String RIGHT = "Вправо";

    private static final Map<String, String> result = Map.ofEntries(
            Map.entry("35612346", UP), Map.entry("1352356", UP),
            Map.entry("23612346", DOWN), Map.entry("13526", DOWN),
            Map.entry("51456", LEFT), Map.entry("545", LEFT),
            Map.entry("134561235", RIGHT), Map.entry("1341235", RIGHT)
    );

    @Override
    public String solve(String message, Long userId) {
        var splitted = Arrays.stream(message.split(" ")).toList();
        return result.get(splitted.get(1));
    }
}
