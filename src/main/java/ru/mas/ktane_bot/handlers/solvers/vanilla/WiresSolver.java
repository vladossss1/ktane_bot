package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;

import java.util.List;

import static ru.mas.ktane_bot.model.CommonValues.*;

@Component("wiresSolver")
@RequiredArgsConstructor
public class WiresSolver implements Solver {

    private final DataCache dataCache;
    private List<Character> wires;
    private static final String LAST_BLUE = "Последний синий";
    private static final String LAST_RED = "Последний красный";


    @SneakyThrows
    @Override
    public MessageDto solve(String message, String userId) {
        wires = message.chars().mapToObj(c -> (char) c).toList();
        var bomb = dataCache.getUserBomb(userId);
        var result = switch (wires.size()) {
            case 3 -> {
                if (!wires.contains('r'))
                    yield TWO;
                else if (lastWire('w') || !moreThanOneWire('b'))
                    yield THREE;
                else
                    yield LAST_BLUE;
            }
            case 4 -> {
                if (moreThanOneWire('r') && bomb.isLastDigitOfSerialNumber(true))
                    yield LAST_RED;
                else if ((lastWire('y') && noWire('r')) || exactlyOneWire('b'))
                    yield ONE;
                else if (moreThanOneWire('y'))
                    yield FOUR;
                else
                    yield TWO;
            }
            case 5 -> {
                if (lastWire('d') && bomb.isLastDigitOfSerialNumber(true))
                    yield FOUR;
                else if ((exactlyOneWire('r') && moreThanOneWire('y')) || !noWire('d'))
                    yield ONE;
                else
                    yield TWO;
            }
            case 6 -> {
                if (noWire('y') && bomb.isLastDigitOfSerialNumber(true))
                    yield THREE;
                else if ((exactlyOneWire('y') && moreThanOneWire('w')) || !noWire('r'))
                    yield FOUR;
                else
                    yield SIX;
            }
            default -> throw new Exception(); // TODO validate
        };
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
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

    private boolean lastWire(char color) {
        return wires.getLast().equals(color);
    }
}
