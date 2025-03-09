package ru.mas.ktane_bot.model.modules.vanilla.needy;

import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.Map;

import static ru.mas.ktane_bot.model.CommonValues.*;
import static ru.mas.ktane_bot.model.CommonValues.RIGHT;

public class KnobsModule extends BombModule {

    public static final Map<String, String> positions = Map.ofEntries(
            Map.entry("35612346", UP), Map.entry("1352356", UP),
            Map.entry("23612346", DOWN), Map.entry("13526", DOWN),
            Map.entry("51456", LEFT), Map.entry("545", LEFT),
            Map.entry("134561235", RIGHT), Map.entry("1341235", RIGHT)
    );
}
