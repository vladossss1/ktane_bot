package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Setter;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;

@Setter
public class WireSeqModule extends BombModule {
    private int blueCount = 0;
    private int redCount = 0;
    private int blackCount = 0;

    public int getRedCount() {
        return redCount++;
    }
    public int getBlueCount() {
        return blueCount++;
    }
    public int getBlackCount() {
        return blackCount++;
    }
}
