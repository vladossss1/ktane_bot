package ru.mas.ktane_bot.model.modules;

import lombok.Getter;
import lombok.Setter;

@Setter
public class WireSeq extends BombModule {
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
