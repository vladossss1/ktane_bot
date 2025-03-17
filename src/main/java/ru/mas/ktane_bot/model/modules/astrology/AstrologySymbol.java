package ru.mas.ktane_bot.model.modules.astrology;

import java.util.Arrays;

public enum AstrologySymbol {
    FIRE("CAACAgIAAxkBAAInNGfVpuqB37bNIFYB6w4UZ1xSu2K_AAJhdgACvnCoShPv4unMJqklNgQ"),
    WATER("CAACAgIAAxkBAAInN2fVpxlyxjFvlIZEf94xiIrhRVBRAAJeagACOmCxSq9XtNpOPkcJNgQ"),
    EARTH("CAACAgIAAxkBAAInOGfVpxqSXhSfZCL9s6uEe0DbNUZEAAK9aAAC4-iwSun-D0jlsZ43NgQ"),
    AIR("CAACAgIAAxkBAAInOWfVpxrUj0ESU9rKcHa6CaY5Vei9AAKaZwACMkyoSmpp0EkQ02X7NgQ"),
    SUN("CAACAgIAAxkBAAInOmfVpxpE19kbi09Z-LhBjM4iswcoAAKmagACLeOpSuKmY-gjai7bNgQ"),
    MOON("CAACAgIAAxkBAAInO2fVpxsj1kkdLKAVw4pq_tV_uCHSAAKHdAACCBSxSn0xX1tPCoKPNgQ"),
    MERCURY("CAACAgIAAxkBAAInPGfVpxvhExtJHQfdXAxiqacPIp3TAAKLbQACqamxSqk1ELBeV2kvNgQ"),
    VENUS("CAACAgIAAxkBAAInPWfVpxxXbVBDXvhlVnlusPJVfFCDAAIpaAACLGyxSqjC_E1WRn6HNgQ"),
    MARS("CAACAgIAAxkBAAInPmfVpxxXVQM2PSjEGw9yDF0EvfuYAAIFbwACt2WpSlcOkgX6Ps8MNgQ"),
    JUPITER("CAACAgIAAxkBAAInP2fVpxxSEoCuW2O9VYPCc4w4OS55AAIGbgAC3ASwSubYw7Br3ZvSNgQ"),
    SATURN("CAACAgIAAxkBAAInQGfVpx0fQk4pyKrkzhznn78rXph8AAKLaAACm1qoSlehRsBnJqWgNgQ"),
    URANUS("CAACAgIAAxkBAAInQWfVpx2RTO10rFHgSNUHZZIo8u5lAAIpbQACym6xSqMT6kyEjQpPNgQ"),
    NEPTUNE("CAACAgIAAxkBAAInQmfVpx4MLtAb5U7hPqBaEIsrUKHYAAJRbAACO7uxSrjyCW73iiH0NgQ"),
    PLUTO("CAACAgIAAxkBAAInQ2fVpx4yIPlL29DaPDvGY-Y7utCWAAI2bQACRVexSniB8aFBV1z_NgQ"),
    ARIES("CAACAgIAAxkBAAInRGfVpx4Phdge2xoGm5zhI-HtqeRxAAKpbAACcMixSvAKD3ddV64LNgQ"),
    TAURUS("CAACAgIAAxkBAAInRWfVpx_RsVrPLAk13D4PTyRnx4jXAAJDagACCJaoSi6zEywv-gllNgQ"),
    GEMINI("CAACAgIAAxkBAAInRmfVpx9klgoyxOUXs-5g3NJv5GwBAAKtbgACA9ipSrSvlQgDHKf8NgQ"),
    CANCER("CAACAgIAAxkBAAInR2fVpyDL6edGZvVNqarecLeKizVkAAJgZgACvTawSto-0j79GCJyNgQ"),
    LEO("CAACAgIAAxkBAAInSGfVpyDrz-Hcy1uYYmlmh3jCz0bBAAJ_bQACSOCoSt0-Ppoc5KZgNgQ"),
    VIRGO("CAACAgIAAxkBAAInSWfVpyGiO5fw3VVKaeSIXK1buBsjAAKVcQACM5ixSrXlgY1yBerhNgQ"),
    LIBRA("CAACAgIAAxkBAAInSmfVpyGp9Xtk4Wel1TktyanaN0ABAAL1dAACq7OxSpIYQBnoSd1FNgQ"),
    SCORPIO("CAACAgIAAxkBAAInS2fVpyJHOIDv5LqNnDvKSIetlUTkAALGZgACqh6wSq94AaNmEE85NgQ"),
    SAGITTARIUS("CAACAgIAAxkBAAInTGfVpyKx0v5G7LGo936q5f-CxiTZAALQbgACzAGwSmTViF2IGfo7NgQ"),
    CAPRICORN("CAACAgIAAxkBAAInTWfVpyLCz1rek4GEy0egZlGyzO8wAAJcdAACSRGpSq826VY4-yzqNgQ"),
    AQUARIUS("CAACAgIAAxkBAAInTmfVpyPWgzfSz02beRP3Z5yvacLCAAK7cAACZG-pSrYVkaCFUuaqNgQ"),
    PISCES("CAACAgIAAxkBAAInT2fVpyPy5z89bftr0MfbjdO2vspfAAK5cQACxN6pSiNhKhpVEkFoNgQ");

    private final String stickerId;

    AstrologySymbol(String stickerId) {
        this.stickerId = stickerId;
    }

    public static AstrologySymbol getByStickerId(String stickerId) {
        return Arrays.stream(AstrologySymbol.values()).filter(s -> s.stickerId.equals(stickerId)).findFirst().get();
    }
}
