package ru.mas.ktane_bot.model.modules.connectioncheck;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ConnectionCheckGraphName {

    SLIM("SLIM"),
    BRO("15BRO"),
    DGT("20DGT"),
    XYZ("34XYZ"),
    HPJ("7HPJ"),
    WUF("6WUF"),
    CAKE("8CAKE"),
    QVN("9QVN");

    private final String name;

    ConnectionCheckGraphName(String name) {
        this.name = name;
    }

    public static ConnectionCheckGraphName getBySymbol(char symbol) {
        return Arrays.stream(ConnectionCheckGraphName.values()).filter(n -> n.name.indexOf(symbol) != -1).findFirst().get();
    }
}
