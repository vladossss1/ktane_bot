package ru.mas.ktane_bot.handlers.solvers.mods.easy;

import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;

import java.util.List;

public class EmojiMathSolver extends Handler {

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

    public EmojiMathSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var splitted = message.split("");
        userDataCache.solveModule(userId);
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
