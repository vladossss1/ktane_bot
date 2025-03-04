package ru.mas.ktane_bot.model.modules;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PianoKeys extends BombModule{

    List<String> symbols = new ArrayList<>();

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
