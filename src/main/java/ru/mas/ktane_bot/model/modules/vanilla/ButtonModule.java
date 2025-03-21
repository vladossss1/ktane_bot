package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

@Getter
@Setter
public class ButtonModule extends BombModule {
    private String color;
    private String name;

    public ButtonModule(String color, String name) {
        super(List.of(BombAttribute.INDICATORS, BombAttribute.BATTERIES));
        this.color = color;
        this.name = name;
    }
}
