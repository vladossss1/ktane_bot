package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

@Getter
@Setter
public class SimonSaysModule extends BombModule {
    String result = "";

    public SimonSaysModule() {
        super(List.of(BombAttribute.SERIALNUMBER));
    }
}
