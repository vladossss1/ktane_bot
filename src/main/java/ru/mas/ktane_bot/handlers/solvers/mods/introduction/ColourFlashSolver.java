package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static ru.mas.ktane_bot.model.CommonValues.*;

@Component("colourFlashSolver")
@RequiredArgsConstructor
public class ColourFlashSolver implements Solver {

    private final DataCache dataCache;

    private static final String WORD = " слове";
    private static final String ON = " на ";

    @Override
    public MessageDto solve(String message, String userId) {
        var splitted = message.split(" ");
        var values = new ArrayList<String>();
        for (int i = 0; i < splitted[0].length(); i++)
            values.add(splitted[0].charAt(i) + String.valueOf(splitted[1].charAt(i)));
        var result = switch (values.getLast().charAt(0)) {
            case 'r':
                if (values.stream().filter(s -> s.charAt(1) == 'g').count() > 2)
                    yield PRESS + YES + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).contains("g")).boxed().toList().get(2) + 1) + WORD;
                else if (values.stream().filter(s -> s.charAt(0) == 'b').count() == 1)
                    yield PRESS + NO + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(1) == 'm').boxed().toList().getFirst() + 1) + WORD;
                else
                    yield PRESS + YES + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).contains("w")).boxed().toList().getLast() + 1) + WORD;
            case 'y':
                if (values.contains("gb"))
                    yield PRESS + YES + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(0) == 'g').boxed().toList().getFirst() + 1) + WORD;
                else if (values.stream().anyMatch(s -> s.matches("ww|rw")))
                    yield PRESS + YES + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(0) != values.get(i).charAt(1)).boxed().toList().get(1) + 1) + WORD;
                else
                    yield PRESS + NO + ON +
                            values.stream().filter(s -> s.contains("m")).count() + WORD;
            case 'g':
                if (IntStream.range(0, values.size() - 1).anyMatch(i -> values.get(i).charAt(1) == values.get(i + 1).charAt(1)
                        && values.get(i).charAt(0) != values.get(i + 1).charAt(0)))
                    yield PRESS + NO + ON + FIVE + WORD;
                else if (values.stream().filter(s -> s.charAt(1) == 'm').count() > 2)
                    yield PRESS + NO + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).contains("y")).boxed().toList().getFirst() + 1) + WORD;
                else
                    yield PRESS + YES + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(0) == values.get(i).charAt(1)).boxed().toList().getFirst() + 1) + WORD;
            case 'b':
                if (values.stream().filter(s -> s.charAt(0) != s.charAt(1)).count() > 2)
                    yield PRESS + YES + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(0) != values.get(i).charAt(1)).boxed().toList().getFirst() + 1) + WORD;
                else if (values.stream().anyMatch(s -> s.matches("yr|wy")))
                    yield PRESS + NO + ON +
                            values.indexOf("rw") + WORD;
                else
                    yield PRESS + YES + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).contains("g")).boxed().toList().getFirst() + 1) + WORD;
            case 'm':
                if (IntStream.range(0, values.size() - 1).anyMatch(i -> values.get(i).charAt(0) == values.get(i + 1).charAt(0)
                        && values.get(i).charAt(1) != values.get(i + 1).charAt(1)))
                    yield PRESS + YES + ON + THREE + WORD;
                else if (values.stream().filter(s -> s.charAt(1) == 'y').count() > values.stream().filter(s -> s.charAt(0) == 'b').count())
                    yield PRESS + NO + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(1) == 'y').boxed().toList().getLast() + 1) + WORD;
                else
                    yield PRESS + NO + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(0) == values.get(6).charAt(1)).boxed().toList().getFirst() + 1) + WORD;
            case 'w':
                if (String.valueOf(values.get(2).charAt(0)).matches(values.get(3).charAt(1) + "|" + values.get(4).charAt(1)))
                    yield PRESS + NO + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).contains("b")).boxed().toList().getFirst() + 1) + WORD;
                else if (values.contains("ry"))
                    yield PRESS + NO + ON +
                            (IntStream.range(0, values.size()).filter(i -> values.get(i).charAt(0) == 'b').boxed().toList().getLast() + 1) + WORD;
                else
                    yield PRESS + NO;
            default:
                throw new RuntimeException(); // TODO validate
        };
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
