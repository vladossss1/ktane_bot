package ru.mas.ktane_bot.model.modules;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mas.ktane_bot.model.bomb.BombAttribute;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public abstract class BombModule {
    int stage = 0;
    List<BombAttribute> requiredAttributes = new ArrayList<>();
    Integer messageWithKeyboardId;

    public void nextStage() {stage += 1;}

    public BombModule(List<BombAttribute> requiredAttributes) {
        this.requiredAttributes.addAll(requiredAttributes);
    }
}
