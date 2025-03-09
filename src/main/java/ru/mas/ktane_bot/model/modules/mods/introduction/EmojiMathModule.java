package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

@Getter
public class EmojiMathModule extends BombModule {
    public static final List<String> emojis = List.of(
            ":)",
            "=(",
            "(:",
            ")=",
            ":(",
            "):",
            "=)",
            "(=",
            ":|",
            "|:"
    );
}
