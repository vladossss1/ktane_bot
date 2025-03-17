package ru.mas.ktane_bot.model.modules.mods.introduction;

import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

public class CodeModule extends BombModule {

    public CodeModule() {
        super(List.of(BombAttribute.SERIALNUMBER, BombAttribute.BATTERIES, BombAttribute.INDICATORS, BombAttribute.PORTS));
    }
}
