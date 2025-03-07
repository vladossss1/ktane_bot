package ru.mas.ktane_bot.model.modules;


import lombok.NoArgsConstructor;
import ru.mas.ktane_bot.model.BombAttribute;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public abstract class BombModule {
    int stage = 0;
    List<BombAttribute> requiredAttributes = new ArrayList<>();
    public int getAndIncrementStage() {
        return stage++;
    }

    public BombModule(List<BombAttribute> requiredAttributes) {
        this.requiredAttributes.addAll(requiredAttributes);
    }
}
