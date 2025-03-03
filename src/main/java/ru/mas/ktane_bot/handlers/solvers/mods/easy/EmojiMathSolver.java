package ru.mas.ktane_bot.handlers.solvers.mods.easy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;

import java.util.List;

@Component("emojiMathSolver")
@RequiredArgsConstructor
public class EmojiMathSolver implements Solver {

    private final DataCache dataCache;

    private static final List<String> emoji = List.of(
            ":)",
            "=(",
            "(:",
            ")=",
            ":(",
            "):",
            "=)",
            "(=",
            ":|",
            "|:"
    );

    @Override
    public String solve(String message, Long userId) {
        var splitted = message.split("");
        dataCache.solveModule(userId);
        var a = Integer.parseInt(emoji.indexOf(splitted[0] + splitted[1])
                + String.valueOf(emoji.indexOf(splitted[2] + splitted[3])));
        var b = Integer.parseInt(emoji.indexOf(splitted[5] + splitted[6])
                + String.valueOf(emoji.indexOf(splitted[7] + splitted[8])));
        if (splitted[4].equals("+"))
            return String.valueOf(a + b);
        else
            return String.valueOf(a - b);
    }
}
