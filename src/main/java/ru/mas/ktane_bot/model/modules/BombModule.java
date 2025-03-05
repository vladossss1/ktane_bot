package ru.mas.ktane_bot.model.modules;


import ru.mas.ktane_bot.model.BombAttribute;

import java.util.ArrayList;
import java.util.List;

public abstract class BombModule {
    int stage = 0;
    List<BombAttribute> requiredAttributes;
    public int getStage() {
        return stage++;
    }

    public BombModule(List<BombAttribute> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }
    public BombModule() {
        this.requiredAttributes = new ArrayList<>();
    }
}
