package ru.mas.ktane_bot.model.modules.vanilla;

import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

public class CompWiresModule extends BombModule {
    public CompWiresModule() {
        super(List.of(BombAttribute.BATTERIES, BombAttribute.SERIALNUMBER, BombAttribute.PORTS));
    }
}
