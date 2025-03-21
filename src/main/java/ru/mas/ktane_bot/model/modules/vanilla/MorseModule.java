package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.Map;

@Getter
@Setter
public class MorseModule extends BombModule {

    public static final Map<String, String> letters = Map.ofEntries(
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

    public static final Map<String, String> words = Map.ofEntries(
            Map.entry("токарь", "3.505 МГц"), Map.entry("мостик", "3.515 МГц"), Map.entry("венок", "3.522 МГц"),
            Map.entry("брать", "3.532 МГц"), Map.entry("клара", "3.535 МГц"), Map.entry("попей", "3.542 МГц"),
            Map.entry("виток", "3.545 МГц"), Map.entry("восток", "3.552 МГц"), Map.entry("вилка", "3.555 МГц"),
            Map.entry("порок", "3.565 МГц"), Map.entry("койка", "3.572 МГц"), Map.entry("рокер", "3.575 МГц"),
            Map.entry("помой", "3.582 МГц"), Map.entry("покой", "3.592 МГц"), Map.entry("борис", "3.595 МГц"),
            Map.entry("порог", "3.600 МГц")
    );
    String result = "";
    public void addLetter(String letter) {
        result += letter;
    }
}
