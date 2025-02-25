package ru.mas.ktane_bot.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PortType {
    DVI_D("D"),
    PARALLEL("P"),
    PS_2("PS2"),
    RJ_45("RJ"),
    SERIAL("S"),
    STEREO_RCA("R"),
    HDMI("H"),
    USB("U");

    private final String type;

    PortType(String type) {
        this.type = type;
    }

    public static PortType get(String type) {
        return Arrays.stream(PortType.values())
                .filter(env -> env.type.equals(type))
                .findFirst().get();
    }

}
