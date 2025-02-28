package ru.mas.ktane_bot.model.modules;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Morse extends BombModule{
    String result = "";

    public void addLetter(String letter) {
        result += letter;
    }
}
