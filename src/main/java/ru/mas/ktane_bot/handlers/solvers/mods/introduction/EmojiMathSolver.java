package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

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
        var result = new StringBuilder();
        var chars = message.toCharArray();
        for (int i = 0; i < chars.length; i += 2) {
            if (chars[i] == '+' || chars[i] == '-')
                result.append(chars[i++]);
            result.append(emoji.indexOf(chars[i] + String.valueOf(chars[i + 1])));
        }
        var splitted = result.toString().split("[+\\-]");
        return String.valueOf(message.contains("+") ? Integer.parseInt(splitted[0]) + Integer.parseInt(splitted[1]) :
                Integer.parseInt(splitted[0]) - Integer.parseInt(splitted[1]));
    }
}
