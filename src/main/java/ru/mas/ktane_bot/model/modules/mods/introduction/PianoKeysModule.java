package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PianoKeysModule extends BombModule {

    public static final String FLAT = "CAACAgIAAxkBAAIfHGfHGMv1c3bZqCjrG4ZXt_JNPkUwAAJGZQAC2MA4SrRQW8DbfvBQNgQ";
    public static final String FERMATA = "CAACAgIAAxkBAAIfHWfHGM0gVOkDqiOLTRwNtfQMkvSxAAIfagACrp04SoNrnhYr4JVvNgQ";
    public static final String CLEF = "CAACAgIAAxkBAAIfHmfHGNPs5KSsKkT4G8Y_5apzVKwBAALWagACdeQ5SqUP9OIyOveKNgQ";
    public static final String MORDENT = "CAACAgIAAxkBAAIfH2fHGNXwBZSkH0rEcMYlklelU6_RAALdZwAC0IU4Si_zO1Hylv6gNgQ";
    public static final String NATURAL = "CAACAgIAAxkBAAIfIGfHGNXwb4LsvIvtD5xMKtP7ZAPTAAKBZgACWAI5Srr5UxdQsN8QNgQ";
    public static final String SHARP = "CAACAgIAAxkBAAIfIWfHGNWkfVORyx4UYZpxEaVBY6YFAALvbQACO2U5Soms2VT40Kx7NgQ";
    public static final String TURN = "CAACAgIAAxkBAAIfImfHGNiV7j_O8v8LeC3kUG2MN8cNAALTZwACXyQ4Svs87rtVqGsgNgQ";
    public static final String COMMON_TIME = "CAACAgIAAxkBAAIfI2fHGNqhSxO3vF8r23PmmwHS3ExyAAIdZwACijRASvaR6ed0BCdKNgQ";
    public static final String CUT_TIME = "CAACAgIAAxkBAAIfJGfHGNvsvZBw-2kQUDtUm0Agd7d4AALGawACz6E5Sg5qcHXjEGsWNgQ";

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
