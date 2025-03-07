package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.lang.reflect.Method;
import java.util.List;

@Getter
@Setter
public class BulbModule extends BombModule {
    boolean rememberedIndicator;
    boolean bulbGotOff;
    boolean isBulbOn;
    String firstStepPressedButton;
    String secondOrThirdStepPressedButton;
    Method currentMethod;
    String description;

    public BulbModule() {
        super(List.of(BombAttribute.INDICATORS));
    }
}
