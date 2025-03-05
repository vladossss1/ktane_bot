package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MorseModule extends BombModule {
    String result = "";
    public void addLetter(String letter) {
        result += letter;
    }
}
