package ru.mas.ktane_bot.model.modules.mods.introduction;

import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

public class SwitchesModule extends BombModule {
    public final static List<List<Boolean>> exceptions = List.of(
            List.of(true, true, false, true, true),
            List.of(true, false, true, false, false),
            List.of(true, false, false, false, false),
            List.of(false, true, true, false, true),
            List.of(false, true, true, false, false),
            List.of(false, true, false, false, false),
            List.of(false, false, true, true, true),
            List.of(false, false, true, false, true),
            List.of(false, false, false, true, true),
            List.of(false, false, false, false, true)
    );
}
