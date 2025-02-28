package ru.mas.ktane_bot.handlers.solvers.vanilla;

import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;
import ru.mas.ktane_bot.model.modules.Morse;

import java.util.Map;

public class MorseSolver extends Handler {

    private static final Map<String, String> letters = Map.ofEntries(
            Map.entry(".-", "а"), Map.entry("-...", "б"), Map.entry(".--", "в"),
            Map.entry("--.", "г"), Map.entry("-..", "д"), Map.entry(".", "е"),
            Map.entry("...-", "ж"), Map.entry("--..", "з"), Map.entry("..", "и"),
            Map.entry(".---", "й"), Map.entry("-.-", "к"), Map.entry(".-..", "л"),
            Map.entry("--", "м"), Map.entry("-.", "н"), Map.entry("---", "о"),
            Map.entry(".--.", "п"), Map.entry(".-.", "р"), Map.entry("...", "с"),
            Map.entry("-", "т"), Map.entry("..-", "у"), Map.entry("..-.", "ф"),
            Map.entry("....", "х"), Map.entry("-.-.", "ц"), Map.entry("---.", "ч"),
            Map.entry("----", "ш"), Map.entry("--.-", "щ"), Map.entry(".--.-.", "ъ"),
            Map.entry("-.--", "ы"), Map.entry("-..-", "ь"), Map.entry("..-..", "э"),
            Map.entry("..--", "ю"), Map.entry(".-.-", "я")
    );

    private static final Map<String, String> words = Map.ofEntries(
            Map.entry("токарь", "3.505 МГц"), Map.entry("мостик", "3.515 МГц"), Map.entry("венок", "3.522 МГц"),
            Map.entry("брать", "3.532 МГц"), Map.entry("клара", "3.535 МГц"), Map.entry("попей", "3.542 МГц"),
            Map.entry("виток", "3.545 МГц"), Map.entry("восток", "3.552 МГц"), Map.entry("вилка", "3.555 МГц"),
            Map.entry("порок", "3.565 МГц"), Map.entry("койка", "3.572 МГц"), Map.entry("рокер", "3.575 МГц"),
            Map.entry("помой", "3.582 МГц"), Map.entry("покой", "3.592 МГц"), Map.entry("борис", "3.595 МГц"),
            Map.entry("порог", "3.600 МГц")
    );

    public MorseSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var letter = letters.get(message);
        var module = (Morse) userDataCache.getUserModule(userId);
        if (module == null) {
            module = new Morse();
            userDataCache.saveUserModule(userId, module);
        }
        module.addLetter(letter);
        var result = module.getResult();
        var resultList = words.keySet().stream().filter(w -> w.startsWith(result)).map(words::get).toList();
        if (resultList.size() > 1) {
            return "Введите еще одну букву";
        } else {
            userDataCache.solveModule(userId);
            return resultList.get(0);
        }
    }
}
