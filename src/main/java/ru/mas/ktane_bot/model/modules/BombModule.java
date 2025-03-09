package ru.mas.ktane_bot.model.modules;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.mas.ktane_bot.model.bomb.BombAttribute;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public abstract class BombModule {
    int stage = 0;
    List<BombAttribute> requiredAttributes = new ArrayList<>();

    public void nextStage() {stage++;}

    public BombModule(List<BombAttribute> requiredAttributes) {
        this.requiredAttributes.addAll(requiredAttributes);
    }
}
