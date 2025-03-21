package ru.mas.ktane_bot.model.bomb;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Indicator {
    private String name;
    private Boolean lit;
}
