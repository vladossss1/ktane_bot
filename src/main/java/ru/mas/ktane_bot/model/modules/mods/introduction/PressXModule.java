package ru.mas.ktane_bot.model.modules.mods.introduction;

import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;
import java.util.Map;

import static ru.mas.ktane_bot.model.CommonValues.*;

public class PressXModule extends BombModule {

    public static final Map<Integer, List<String>> buttonsMap = Map.ofEntries(
            Map.entry(0, List.of(A, B, Y)),
            Map.entry(1, List.of(X, Y, A)),
            Map.entry(2, List.of(B, A, X)),
            Map.entry(3, List.of(Y, X, B))
    );

    public PressXModule() {
        super(List.of(BombAttribute.SERIALNUMBER, BombAttribute.BATTERIES, BombAttribute.INDICATORS));
    }
}
