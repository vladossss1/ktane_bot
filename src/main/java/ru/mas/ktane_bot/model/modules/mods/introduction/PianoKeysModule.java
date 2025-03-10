package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import ru.mas.ktane_bot.model.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PianoKeysModule extends BombModule {

    List<String> symbols = new ArrayList<>();

    public PianoKeysModule() {
        super(List.of(BombAttribute.SERIALNUMBER, BombAttribute.BATTERIES, BombAttribute.PORTS, BombAttribute.INDICATORS));
    }

    public void addSymbol(String sticker) {
        symbols.add(sticker);
    }

    public boolean hasSymbol(String symbol) {
        return symbols.contains(symbol);
    }

    public boolean hasOneOfSymbols(String pattern) {
        return symbols.stream().anyMatch(s -> s.matches(pattern));
    }

    public boolean hasAllSymbols(List<String> symbols) {
        return (this.symbols).containsAll(symbols);
    }
}
