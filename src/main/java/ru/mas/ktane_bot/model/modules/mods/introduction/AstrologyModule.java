package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;
import ru.mas.ktane_bot.model.modules.astrology.AstrologySymbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.mas.ktane_bot.model.modules.astrology.AstrologySymbol.*;

@Getter
public class AstrologyModule extends BombModule {

    public AstrologyModule() {
        super(List.of(BombAttribute.SERIALNUMBER));
    }

    List<AstrologySymbol> symbols = new ArrayList<>();
    public void addSymbol(AstrologySymbol symbol) {
        symbols.add(symbol);
    }

    public int getTableValue() {
        return tables.get(symbols.get(0)).get(symbols.get(1)) + tables.get(symbols.get(0)).get(symbols.get(2))
                + tables.get(symbols.get(1)).get(symbols.get(2));
    }

    private final Map<AstrologySymbol, Map<AstrologySymbol, Integer>> tables = Map.ofEntries(
            Map.entry(FIRE, Map.ofEntries(
                    Map.entry(SUN, 0),
                    Map.entry(MOON, 0),
                    Map.entry(MERCURY, 1),
                    Map.entry(VENUS, -1),
                    Map.entry(MARS, 0),
                    Map.entry(JUPITER, 1),
                    Map.entry(SATURN, -2),
                    Map.entry(URANUS, 2),
                    Map.entry(NEPTUNE, 0),
                    Map.entry(PLUTO, -1),
                    Map.entry(ARIES, -1),
                    Map.entry(TAURUS, -1),
                    Map.entry(GEMINI, 2),
                    Map.entry(CANCER, 0),
                    Map.entry(LEO, -1),
                    Map.entry(VIRGO, 2),
                    Map.entry(LIBRA, -1),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 0),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, -2),
                    Map.entry(PISCES, -2)
            )),
            Map.entry(WATER, Map.ofEntries(
                    Map.entry(SUN, -2),
                    Map.entry(MOON, 0),
                    Map.entry(MERCURY, -1),
                    Map.entry(VENUS, 0),
                    Map.entry(MARS, 2),
                    Map.entry(JUPITER, 0),
                    Map.entry(SATURN, -2),
                    Map.entry(URANUS, 2),
                    Map.entry(NEPTUNE, 0),
                    Map.entry(PLUTO, 1),
                    Map.entry(ARIES, 2),
                    Map.entry(TAURUS, 2),
                    Map.entry(GEMINI, -1),
                    Map.entry(CANCER, 2),
                    Map.entry(LEO, -1),
                    Map.entry(VIRGO, -1),
                    Map.entry(LIBRA, -2),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 2),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, 0),
                    Map.entry(PISCES, 2)
            )),
            Map.entry(EARTH, Map.ofEntries(
                    Map.entry(SUN, -1),
                    Map.entry(MOON, -1),
                    Map.entry(MERCURY, 0),
                    Map.entry(VENUS, -1),
                    Map.entry(MARS, 1),
                    Map.entry(JUPITER, 2),
                    Map.entry(SATURN, 0),
                    Map.entry(URANUS, 2),
                    Map.entry(NEPTUNE, 1),
                    Map.entry(PLUTO, -2),
                    Map.entry(ARIES, -2),
                    Map.entry(TAURUS, -1),
                    Map.entry(GEMINI, 0),
                    Map.entry(CANCER, 0),
                    Map.entry(LEO, 1),
                    Map.entry(VIRGO, 0),
                    Map.entry(LIBRA, 1),
                    Map.entry(SCORPIO, 2),
                    Map.entry(SAGITTARIUS, -1),
                    Map.entry(CAPRICORN, -2),
                    Map.entry(AQUARIUS, 1),
                    Map.entry(PISCES, 1)
            )),
            Map.entry(AIR, Map.ofEntries(
                    Map.entry(SUN, -1),
                    Map.entry(MOON, 2),
                    Map.entry(MERCURY, -1),
                    Map.entry(VENUS, 0),
                    Map.entry(MARS, -2),
                    Map.entry(JUPITER, -1),
                    Map.entry(SATURN, 0),
                    Map.entry(URANUS, 2),
                    Map.entry(NEPTUNE, -2),
                    Map.entry(PLUTO, 2),
                    Map.entry(ARIES, 2),
                    Map.entry(TAURUS, 1),
                    Map.entry(GEMINI, 1),
                    Map.entry(CANCER, -2),
                    Map.entry(LEO, -2),
                    Map.entry(VIRGO, 0),
                    Map.entry(LIBRA, -1),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 0),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, -1),
                    Map.entry(PISCES, -1)
            )),
            Map.entry(SUN, Map.ofEntries(
                    Map.entry(ARIES, -1),
                    Map.entry(TAURUS, -1),
                    Map.entry(GEMINI, 2),
                    Map.entry(CANCER, 0),
                    Map.entry(LEO, -1),
                    Map.entry(VIRGO, 0),
                    Map.entry(LIBRA, -1),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 0),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, -2),
                    Map.entry(PISCES, -2)
            )),
            Map.entry(MOON, Map.ofEntries(
                    Map.entry(ARIES, -2),
                    Map.entry(TAURUS, 0),
                    Map.entry(GEMINI, 1),
                    Map.entry(CANCER, 0),
                    Map.entry(LEO, 2),
                    Map.entry(VIRGO, 0),
                    Map.entry(LIBRA, -1),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 2),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, 1),
                    Map.entry(PISCES, 0)
            )),
            Map.entry(MERCURY, Map.ofEntries(
                    Map.entry(ARIES, -2),
                    Map.entry(TAURUS, -2),
                    Map.entry(GEMINI, -1),
                    Map.entry(CANCER, -1),
                    Map.entry(LEO, 1),
                    Map.entry(VIRGO, -1),
                    Map.entry(LIBRA, 0),
                    Map.entry(SCORPIO, -2),
                    Map.entry(SAGITTARIUS, 0),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, -1),
                    Map.entry(PISCES, 1)
            )),
            Map.entry(VENUS, Map.ofEntries(
                    Map.entry(ARIES, -2),
                    Map.entry(TAURUS, 2),
                    Map.entry(GEMINI, -2),
                    Map.entry(CANCER, 0),
                    Map.entry(LEO, 0),
                    Map.entry(VIRGO, 1),
                    Map.entry(LIBRA, -1),
                    Map.entry(SCORPIO, 0),
                    Map.entry(SAGITTARIUS, 2),
                    Map.entry(CAPRICORN, -2),
                    Map.entry(AQUARIUS, -1),
                    Map.entry(PISCES, 1)
            )),
            Map.entry(MARS, Map.ofEntries(
                    Map.entry(ARIES, -2),
                    Map.entry(TAURUS, 0),
                    Map.entry(GEMINI, -1),
                    Map.entry(CANCER, -2),
                    Map.entry(LEO, -2),
                    Map.entry(VIRGO, -2),
                    Map.entry(LIBRA, -1),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 1),
                    Map.entry(CAPRICORN, 1),
                    Map.entry(AQUARIUS, 0),
                    Map.entry(PISCES, -1)
            )),
            Map.entry(JUPITER, Map.ofEntries(
                    Map.entry(ARIES, -1),
                    Map.entry(TAURUS, -2),
                    Map.entry(GEMINI, 1),
                    Map.entry(CANCER, -1),
                    Map.entry(LEO, 0),
                    Map.entry(VIRGO, 0),
                    Map.entry(LIBRA, 0),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 0),
                    Map.entry(CAPRICORN, -1),
                    Map.entry(AQUARIUS, 2),
                    Map.entry(PISCES, 0)
            )),
            Map.entry(SATURN, Map.ofEntries(
                    Map.entry(ARIES, -1),
                    Map.entry(TAURUS, -1),
                    Map.entry(GEMINI, 0),
                    Map.entry(CANCER, 0),
                    Map.entry(LEO, 1),
                    Map.entry(VIRGO, 1),
                    Map.entry(LIBRA, 0),
                    Map.entry(SCORPIO, 0),
                    Map.entry(SAGITTARIUS, 0),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, -1),
                    Map.entry(PISCES, -1)
            )),
            Map.entry(URANUS, Map.ofEntries(
                    Map.entry(ARIES, -1),
                    Map.entry(TAURUS, 2),
                    Map.entry(GEMINI, 0),
                    Map.entry(CANCER, 0),
                    Map.entry(LEO, 1),
                    Map.entry(VIRGO, -2),
                    Map.entry(LIBRA, 1),
                    Map.entry(SCORPIO, 0),
                    Map.entry(SAGITTARIUS, 2),
                    Map.entry(CAPRICORN, -1),
                    Map.entry(AQUARIUS, 1),
                    Map.entry(PISCES, 0)
            )),
            Map.entry(NEPTUNE, Map.ofEntries(
                    Map.entry(ARIES, 1),
                    Map.entry(TAURUS, 0),
                    Map.entry(GEMINI, 2),
                    Map.entry(CANCER, 1),
                    Map.entry(LEO, -1),
                    Map.entry(VIRGO, 1),
                    Map.entry(LIBRA, 1),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 0),
                    Map.entry(CAPRICORN, -2),
                    Map.entry(AQUARIUS, 2),
                    Map.entry(PISCES, 0)
            )),
            Map.entry(PLUTO, Map.ofEntries(
                    Map.entry(ARIES, -1),
                    Map.entry(TAURUS, 0),
                    Map.entry(GEMINI, 0),
                    Map.entry(CANCER, -1),
                    Map.entry(LEO, -2),
                    Map.entry(VIRGO, 1),
                    Map.entry(LIBRA, 2),
                    Map.entry(SCORPIO, 1),
                    Map.entry(SAGITTARIUS, 1),
                    Map.entry(CAPRICORN, 0),
                    Map.entry(AQUARIUS, 0),
                    Map.entry(PISCES, -1)
            ))
    );
}
