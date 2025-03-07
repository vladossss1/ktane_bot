package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CrazyTalkModule extends BombModule {

    List<String> currentWords = new ArrayList<>();

    String currentResult = "";
}
