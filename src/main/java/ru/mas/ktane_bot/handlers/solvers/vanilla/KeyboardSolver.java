package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component("keyboardSolver")
@RequiredArgsConstructor
public class KeyboardSolver implements Solver {

    private static final List<String> firstColumn = List.of("зеркало", "ат", "лямбда", "ножницы", "ю", "гроб", "э");
    private static final List<String> secondColumn = List.of("дрон", "зеркало", "э", "свинья", "бзвезда", "гроб", "?");
    private static final List<String> thirdColumn = List.of("копирайт", "сиськи", "свинья", "о", "з", "лямбда", "бзвезда");
    private static final List<String> fourthColumn = List.of("z", "параграф", "коммент", "ю", "о", "?", "смайлик");
    private static final List<String> fifthColumn = List.of("трезубец", "смайлик", "коммент", "С.", "параграф", "зтв", "чзвезда");
    private static final List<String> sixthColumn = List.of("z", "дрон", "гантеля", "ае", "трезубец", "треугольник", "омега");
    private static final List<List<String>> table = List.of(firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn);

    private final DataCache dataCache;

    @Override
    public String solve(String message, Long userId) {
        var buttons = new ArrayList<>(Arrays.stream(message.split(" ")).toList());
        for (var column : table) {
            if (column.containsAll(buttons)) {
                buttons.sort(Comparator.comparingInt(column::indexOf));
                dataCache.solveModule(userId);
                return buttons.toString();
            }
        }
        return null;
    }
}
