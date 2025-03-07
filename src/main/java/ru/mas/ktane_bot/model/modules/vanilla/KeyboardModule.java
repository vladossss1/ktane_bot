package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KeyboardModule extends BombModule {

    List<String> stickers = new ArrayList<>();

    public void addSticker(String sticker) {
        stickers.add(sticker);
    }
}
