package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.modules.Password;

import java.util.Arrays;
import java.util.List;

@Component("passwordSolver")
@RequiredArgsConstructor
public class PasswordSolver implements Solver {

    private final DataCache dataCache;

    private static final List<String> WORDS = Arrays.asList(
            "аллея", "бомба", "вверх", "взрыв", "внизу",
            "вьюга", "горох", "готов", "густо", "давай",
            "давно", "книга", "конец", "лилия", "линия",
            "можно", "назад", "нравы", "песец", "песня",
            "порох", "порыв", "потом", "право", "пусто",
            "румба", "скоро", "супер", "травы", "тумба",
            "тунец", "фугас", "шприц", "щипок", "щипцы"
    );

    @Override
    public String solve(String message, Long userId) {
        var substate = dataCache.getUsersCurrentBotSubState(userId);
        return switch (substate) {
            case PASSWORD1 -> checkWords(substate, message, new Password(), userId);
            case PASSWORD2, PASSWORD3, PASSWORD4 ->
                    checkWords(substate, message, (Password) dataCache.getUserModule(userId), userId);
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
            currentWords = WORDS.stream().filter(w -> letters.contains(w.toCharArray()[stage])).toList();
        else
            currentWords = password.getCurrentWords().stream().filter(w -> letters.contains(w.toCharArray()[stage])).toList();
        if (currentWords.size() == 1) {
            dataCache.solveModule(userId);
            return currentWords.get(0);
        }
        else {
            dataCache.setUsersCurrentBotSubState(userId,
                    stage == 0 ? BotSubState.PASSWORD2 : stage == 1 ? BotSubState.PASSWORD3 : BotSubState.PASSWORD4);
            password.setCurrentWords(currentWords);
            dataCache.saveUserModule(userId, password);
            return "Введите следующий список букв";
        }

    }
}
