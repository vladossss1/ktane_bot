package ru.mas.ktane_bot.model.modules;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Keyboard extends BombModule{

    List<String> stickers = new ArrayList<>();

    public void addSticker(String sticker) {
        stickers.add(sticker);
    }
}
