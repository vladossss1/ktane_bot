package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.List;

@Getter
public class TwoBitsModule extends BombModule {

    List<List<String>> matrix = List.of(
            List.of("kb", "dk", "gv", "tk", "pv", "kp", "bv", "vt", "pz", "dt"),
            List.of("ee", "zk", "ke", "ck", "zp", "pp", "tp", "tg", "pd", "pt"),
            List.of("tz", "eb", "ec", "cc", "cz", "zv", "cv", "gc", "bt", "gt"),
            List.of("bz", "pk", "kz", "kg", "vd", "ce", "vb", "kd", "gg", "dg"),
            List.of("pb", "vv", "ge", "kv", "dz", "pe", "db", "cd", "td", "cb"),
            List.of("gb", "tv", "kk", "bg", "bp", "vp", "ep", "tt", "ed", "zg"),
            List.of("de", "dd", "ev", "te", "zd", "bb", "pc", "bd", "kc", "zb"),
            List.of("eg", "bc", "tc", "ze", "zc", "gp", "et", "vc", "tb", "vz"),
            List.of("ez", "ek", "dv", "cg", "ve", "dp", "bk", "pg", "gk", "gz"),
            List.of("kt", "ct", "zz", "vg", "gd", "cp", "be", "zt", "vk", "dc")
    );

    public TwoBitsModule() {
        super(List.of(BombAttribute.SERIALNUMBER, BombAttribute.PORTS, BombAttribute.BATTERIES));
    }
}
