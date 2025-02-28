package ru.mas.ktane_bot.model.modules;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Password extends BombModule {
    List<String> currentWords;

}

