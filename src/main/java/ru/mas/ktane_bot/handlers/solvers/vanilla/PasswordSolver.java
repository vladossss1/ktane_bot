package ru.mas.ktane_bot.handlers.solvers.vanilla;

import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;
import ru.mas.ktane_bot.model.Bomb;
import ru.mas.ktane_bot.model.modules.Password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordSolver extends Handler {

    private static List<String> words = Arrays.asList(
            "аллея", "бомба", "вверх", "взрыв", "внизу",
            "вьюга", "горох", "готов", "густо", "давай",
            "давно", "книга", "конец", "лилия", "линия",
            "можно", "назад", "нравы", "песец", "песня",
            "порох", "порыв", "потом", "право", "пусто",
            "румба", "скоро", "супер", "травы", "тумба",
            "тунец", "фугас", "шприц", "щипок", "щипцы"
    );

    public PasswordSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var substate = userDataCache.getUsersCurrentBotSubState(userId);
        return switch (substate) {
            case PASSWORD1 -> checkWords(substate, message, new Password(), userId);
            case PASSWORD2, PASSWORD3, PASSWORD4 ->
                    checkWords(substate, message, (Password) userDataCache.getUserModule(userId), userId);
            default -> null;
        };
    }

    private String checkWords(BotSubState subState, String message, Password password, Long userId) {
        var letters = message.chars().mapToObj(c -> (char)c).toList();
        List<String> currentWords;
        int stage = subState.equals(BotSubState.PASSWORD1) ?
                0 : subState.equals(BotSubState.PASSWORD2) ?
                1 : subState.equals(BotSubState.PASSWORD3) ?
                2 : 3;
        if (subState.equals(BotSubState.PASSWORD1))
            currentWords = words.stream().filter(w -> letters.contains(w.toCharArray()[stage])).toList();
        else
            currentWords = password.getCurrentWords().stream().filter(w -> letters.contains(w.toCharArray()[stage])).toList();
        if (currentWords.size() == 1) {
            userDataCache.solveModule(userId);
            return currentWords.get(0);
        }
        else {
            userDataCache.setUsersCurrentBotSubState(userId,
                    stage == 0 ? BotSubState.PASSWORD2 : stage == 1 ? BotSubState.PASSWORD3 : BotSubState.PASSWORD4);
            password.setCurrentWords(currentWords);
            userDataCache.saveUserModule(userId, password);
            return "Введите следующий список букв";
        }

    }
}
