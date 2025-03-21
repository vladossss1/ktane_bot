package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Setter;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

import static ru.mas.ktane_bot.model.CommonValues.CUT;
import static ru.mas.ktane_bot.model.CommonValues.DONT_CUT;

@Setter
public class WireSeqModule extends BombModule {
    private static final List<List<Character>> red = List.of(
            List.of('c'),
            List.of('b'),
            List.of('a'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('a', 'c'),
            List.of('a', 'b', 'c'),
            List.of('a', 'b'),
            List.of('b')
    );

    private static final List<List<Character>> blue = List.of(
            List.of('b'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('a'),
            List.of('b'),
            List.of('b', 'c'),
            List.of('c'),
            List.of('a', 'c'),
            List.of('a')
    );

    private static final List<List<Character>> black = List.of(
            List.of('a', 'b', 'c'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('a', 'c'),
            List.of('b'),
            List.of('b', 'c'),
            List.of('a', 'b'),
            List.of('c'),
            List.of('c')
    );

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

    public String calculateCut(char color, char letter) {
        return switch (color) {
            case 'r' -> red.get(getRedCount()).contains(letter) ? CUT : DONT_CUT;
            case 'b' -> blue.get(getBlueCount()).contains(letter) ? CUT : DONT_CUT;
            default -> black.get(getBlackCount()).contains(letter) ? CUT : DONT_CUT;
        };
    }
}
