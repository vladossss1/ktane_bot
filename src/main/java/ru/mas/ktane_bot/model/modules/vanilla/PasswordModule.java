package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class PasswordModule extends BombModule {
    List<String> currentWords = Arrays.asList(
            "аллея", "бомба", "вверх", "взрыв", "внизу",
            "вьюга", "горох", "готов", "густо", "давай",
            "давно", "книга", "конец", "лилия", "линия",
            "можно", "назад", "нравы", "песец", "песня",
            "порох", "порыв", "потом", "право", "пусто",
            "румба", "скоро", "супер", "травы", "тумба",
            "тунец", "фугас", "шприц", "щипок", "щипцы"
    );
}

