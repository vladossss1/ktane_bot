package ru.mas.ktane_bot.handlers.solvers;

import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;
import ru.mas.ktane_bot.model.modules.SimonSays;

public class SimonSaysSolver extends Handler {

    private static final String BLUE = "Синяя";
    private static final String RED = "Красная";
    private static final String GREEN = "Зелёная";
    private static final String YELLOW = "Жёлтая";
    public SimonSaysSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var bomb = userDataCache.getUserBomb(userId);
        if (userDataCache.getUserModule(userId) == null)
            userDataCache.saveUserModule(userId, new SimonSays());
        var module = (SimonSays) userDataCache.getUserModule(userId);
        if (message.equals("стоп")) {
            userDataCache.solveModule(userId);
            return "Решено";
        }
        if (bomb.serialHasVowel()) {
            switch (bomb.getErrorsCount()) {
                case 0:
                    if (calculate(message, userId, module, BLUE, RED, YELLOW, GREEN)) return module.getResult();
                case 1:
                    if (calculate(message, userId, module, YELLOW, GREEN, BLUE, RED)) return module.getResult();
                case 2:
                    if (calculate(message, userId, module, GREEN, RED, YELLOW, BLUE)) return module.getResult();
            }
        }
        else {
            switch (bomb.getErrorsCount()) {
                case 0:
                    if (calculate(message, userId, module, BLUE, YELLOW, GREEN, RED)) return module.getResult();
                case 1:
                    if (calculate(message, userId, module, RED, BLUE, YELLOW, GREEN)) return module.getResult();
                case 2:
                    if (calculate(message, userId, module, YELLOW, GREEN, BLUE, RED)) return module.getResult();
            }
        }
        return null;
    }

    private boolean calculate(String message, Long userId, SimonSays module, String r, String b, String g, String y) {
        switch (message) {
            case "r":
                module.setResult(module.getResult() + r + " ");
                userDataCache.saveUserModule(userId, module);
                return true;
            case "b":
                module.setResult(module.getResult() + b + " ");
                userDataCache.saveUserModule(userId, module);
                return true;
            case "g":
                module.setResult(module.getResult() + g + " ");
                userDataCache.saveUserModule(userId, module);
                return true;
            case "y":
                module.setResult(module.getResult() + y + " ");
                userDataCache.saveUserModule(userId, module);
                return true;
        }
        return false;
    }
}
