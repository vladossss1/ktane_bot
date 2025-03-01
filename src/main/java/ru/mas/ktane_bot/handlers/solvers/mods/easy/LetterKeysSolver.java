package ru.mas.ktane_bot.handlers.solvers.mods.easy;

import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;

import java.util.regex.Pattern;

import static ru.mas.ktane_bot.model.modules.CommonValues.*;

public class LetterKeysSolver extends Handler {
    public LetterKeysSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        userDataCache.solveModule(userId);
        var number = Integer.parseInt(message);
        var bomb = userDataCache.getUserBomb(userId);
        if (number == 69)
            return D;
        else if (number % 6 == 0)
            return A;
        else if (number % 3 == 0 && bomb.getBatteriesCount() > 1)
            return B;
        else if (Pattern.compile("[ce3]", Pattern.CASE_INSENSITIVE).matcher(bomb.getSerialNumber()).find()) {
            if (number > 21 && number < 80)
                return B;
            else
                return C;
        }
        else if (number < 46)
            return D;
        else
            return A;
    }
}
