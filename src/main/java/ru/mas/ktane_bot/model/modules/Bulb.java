package ru.mas.ktane_bot.model.modules;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class Bulb extends BombModule {
    boolean rememberedIndicator;
    boolean bulbGotOff;
    boolean isBulbOn;
    String firstStepPressedButton;
    String secondOrThirdStepPressedButton;
    Method currentMethod;
    String description;
}
