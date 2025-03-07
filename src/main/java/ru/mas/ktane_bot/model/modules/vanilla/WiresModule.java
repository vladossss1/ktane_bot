package ru.mas.ktane_bot.model.modules.vanilla;

import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

public class WiresModule extends BombModule {
    public WiresModule() {
        super(List.of(BombAttribute.SERIALNUMBER));
    }
}
