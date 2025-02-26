package ru.mas.ktane_bot.handlers.solvers;

import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;

import java.util.*;

@Component
public class KeyboardSolver extends Handler {

    private static final List<String> firstColumn = List.of("зеркало", "ат", "лямбда", "ножницы", "ю", "гроб", "э");
    private static final List<String> secondColumn = List.of("дрон", "зеркало", "э", "свинья", "бзвезда", "гроб", "?");
    private static final List<String> thirdColumn = List.of("копирайт", "сиськи", "свинья", "о", "з", "лямбда", "бзвезда");
    private static final List<String> fourthColumn = List.of("z", "параграф", "коммент", "ю", "о", "?", "смайлик");
    private static final List<String> fifthColumn = List.of("трезубец", "смайлик", "коммент", "С.", "параграф", "зтв", "чзвезда");
    private static final List<String> sixthColumn = List.of("z", "дрон", "гантеля", "ае", "трезубец", "треугольник", "омега");
    private static final List<List<String>> table = List.of(firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn);

    public KeyboardSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var buttons = new ArrayList<>(Arrays.stream(message.split(" ")).toList());
        var bomb = userDataCache.getUserBomb(userId);
        for (var column : table) {
            if (column.containsAll(buttons)) {
                buttons.sort(Comparator.comparingInt(column::indexOf));
                bomb.solveModule();
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);
                return buttons.toString();
            }
        }
        return null;
    }
}
