package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemoryModule extends BombModule {
    private List<String> positions = new ArrayList<>(4);
    private List<String> values = new ArrayList<>(4);

    public void putPosition(String position) {
        positions.add(position);
    }

    public void putValue(String value) {
        values.add(value);
    }
}
