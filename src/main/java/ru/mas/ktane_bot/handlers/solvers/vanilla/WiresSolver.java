package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;

import java.util.List;

@Component("wiresSolver")
@RequiredArgsConstructor
public class WiresSolver implements Solver {

    private final DataCache dataCache;

    private List<Character> wires;
    private static final String FIRST = "Первый";
    private static final String SECOND = "Второй";
    private static final String THIRD = "Третий";
    private static final String FOURTH = "Четвертый";
    private static final String LAST = "Последний";
    private static final String LAST_BLUE = "Последний синий";
    private static final String LAST_RED = "Последний красный";


    @Override
    public String solve(String message, Long userId) {
        wires = message.chars().mapToObj(c -> (char)c).toList();
        var bomb = dataCache.getUserBomb(userId);
        dataCache.solveModule(userId);
        switch (wires.size()) {
            case 3:
                if (!wires.contains('r'))
                    return SECOND;
                else if (lastWire('w') || !moreThanOneWire('b'))
                    return LAST;
                else
                    return LAST_BLUE;
            case 4:
                if (moreThanOneWire('r') && bomb.isLastDigit(true))
                    return LAST_RED;
                else if ((lastWire('y') && noWire('r')) || exactlyOneWire('b'))
                    return FIRST;
                else if (moreThanOneWire('y'))
                    return LAST;
                else
                    return SECOND;
            case 5:
                if (lastWire('d') && bomb.isLastDigit(true))
                    return FOURTH;
                else if ((exactlyOneWire('r') && moreThanOneWire('y')) || !noWire('d'))
                    return FIRST;
                else
                    return SECOND;
            case 6:
                if (noWire('y') && bomb.isLastDigit(true))
                    return THIRD;
                else if ((exactlyOneWire('y') && moreThanOneWire('w')) || !noWire('r'))
                    return FOURTH;
                else
                    return LAST;
        }
        return null;
    }

    private boolean noWire(char color) {
        return wires.stream().noneMatch(w -> w.equals(color));
    }

    private boolean exactlyOneWire(char color) {
        return wires.stream().filter(w -> w.equals(color)).count() == 1;
    }

    private boolean moreThanOneWire(char color) {
        return wires.stream().filter(w -> w.equals(color)).count() > 1;
    }

    private boolean lastWire(char color){
        return wires.getLast().equals(color);
    }
}
