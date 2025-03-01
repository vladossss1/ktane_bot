package ru.mas.ktane_bot.handlers.solvers.mods.easy;

import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;

import java.util.ArrayList;
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
        var splitted_by_plus = message.split("\\+");
        userDataCache.solveModule(userId);
        int answer = 0;
        if (splitted_by_plus.length > 1) {
            for (int i = 0; i < splitted_by_plus.length; i++) {
                var result = new ArrayList<Integer>();
                mapEmoji(splitted_by_plus, i, result);
                answer += result.stream().reduce((a, b) -> 10 * a + b).get();
            }
            return String.valueOf(answer);
        }
        else {
            var splitted_by_minus = message.split("-");
            for (int i = 0; i < splitted_by_minus.length; i++) {
                var result = new ArrayList<Integer>();
                mapEmoji(splitted_by_minus, i, result);
                if (i == 0)
                    answer += result.stream().reduce((a, b) -> 10 * a + b).get();
                else
                    answer -= result.stream().reduce((a, b) -> 10 * a + b).get();
            }
            return String.valueOf(answer);
        }
    }

    private void mapEmoji(String[] splitted, int i, List<Integer> result) {
        var arr = splitted[i].toCharArray();
        for (int j = 0; j < arr.length; j += 2) {
            result.add(emoji.indexOf(arr[j] + String.valueOf(arr[j + 1])));
        }
    }
}
