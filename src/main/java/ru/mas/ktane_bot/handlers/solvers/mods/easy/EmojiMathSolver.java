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
        userDataCache.solveModule(userId);
        for (int i = 0; i < emoji.size() - 1; i++) {
            message = message.replaceAll(emoji.get(i), String.valueOf(i));
        }
        var splitted = message.split("[+\\-]");
        return String.valueOf(message.contains("+") ? Integer.parseInt(splitted[0]) + Integer.parseInt(splitted[1]) :
                Integer.parseInt(splitted[0]) - Integer.parseInt(splitted[1]));
    }
}
