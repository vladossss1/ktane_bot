package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
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


    @Override
    public MessageDto solve(String message, String userId) {
        wires = message.chars().mapToObj(c -> (char)c).toList();
        var bomb = dataCache.getUserBomb(userId);
        dataCache.solveModule(userId);
        switch (wires.size()) {
            case 3:
                if (!wires.contains('r'))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(TWO).build();
                else if (lastWire('w') || !moreThanOneWire('b'))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(THREE).build();
                else
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(LAST_BLUE).build();
            case 4:
                if (moreThanOneWire('r') && bomb.isLastDigit(true))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(LAST_RED).build();
                else if ((lastWire('y') && noWire('r')) || exactlyOneWire('b'))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(ONE).build();
                else if (moreThanOneWire('y'))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(FOUR).build();
                else
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(TWO).build();
            case 5:
                if (lastWire('d') && bomb.isLastDigit(true))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(FOUR).build();
                else if ((exactlyOneWire('r') && moreThanOneWire('y')) || !noWire('d'))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(ONE).build();
                else
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(TWO).build();
            case 6:
                if (noWire('y') && bomb.isLastDigit(true))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(THREE).build();
                else if ((exactlyOneWire('y') && moreThanOneWire('w')) || !noWire('r'))
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(FOUR).build();
                else
                    return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(SIX).build();
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
