package ru.mas.ktane_bot.model.modules.mods.introduction;

import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

public class LetterKeysModule extends BombModule {
    public LetterKeysModule() {
        super(List.of(BombAttribute.BATTERIES, BombAttribute.SERIALNUMBER));
    }
}
