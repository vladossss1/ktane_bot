package ru.mas.ktane_bot.model.modules;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CrazyTalk extends BombModule{

    List<String> currentWords = new ArrayList<>();

    String currentResult = "";
}
