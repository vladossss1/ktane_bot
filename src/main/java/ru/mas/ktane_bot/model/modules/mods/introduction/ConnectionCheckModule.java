package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.SneakyThrows;
import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;
import ru.mas.ktane_bot.model.modules.connectioncheck.ConnectionCheckGraphName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectionCheckModule extends BombModule {
    Map<Integer, List<Integer>> nodes;

    public ConnectionCheckModule() {
        super(List.of(BombAttribute.SERIALNUMBER, BombAttribute.BATTERIES));
    }

    @SneakyThrows
    public void loadGraph(ConnectionCheckGraphName name) {
        nodes = switch (name) {
            case SLIM:
                yield Map.ofEntries(Map.entry(1, List.of(2, 3, 6)), Map.entry(2, List.of(1, 6)),
                        Map.entry(3, List.of(1, 6)), Map.entry(4, List.of(3, 5, 6)), Map.entry(5, List.of(4, 6, 7)),
                        Map.entry(6, List.of(1, 3, 4)), Map.entry(7, List.of(4, 8)), Map.entry(8, List.of(4, 7)));
            case BRO:
                yield Map.ofEntries(Map.entry(1, List.of(2, 7)), Map.entry(2, List.of(1, 7)),
                        Map.entry(3, List.of(4, 8)), Map.entry(4, List.of(3, 8)), Map.entry(5, List.of(6, 7)),
                        Map.entry(6, List.of(5, 7)), Map.entry(7, List.of(1, 2, 5, 6)), Map.entry(8, List.of(3, 4)));
            case DGT:
                yield Map.ofEntries(Map.entry(1, List.of(2, 3)), Map.entry(2, List.of(1, 4, 7)),
                        Map.entry(3, List.of(1, 5, 7)), Map.entry(4, List.of(2, 6, 7, 8)), Map.entry(5, List.of(3, 6, 7)),
                        Map.entry(6, List.of(4, 5, 8)), Map.entry(7, List.of(2, 3, 4, 5)), Map.entry(8, List.of(4, 6)));
            case XYZ:
                yield Map.ofEntries(Map.entry(1, List.of(2, 4, 6)), Map.entry(2, List.of(1, 3, 4)),
                        Map.entry(3, List.of(2)), Map.entry(4, List.of(1, 2, 7)), Map.entry(5, List.of(6)),
                        Map.entry(6, List.of(1, 5, 8)), Map.entry(7, List.of(4, 6, 8)), Map.entry(8, List.of(6, 7)));
            case HPJ:
                yield Map.ofEntries(Map.entry(1, List.of(2, 3)), Map.entry(2, List.of(1, 3)),
                        Map.entry(3, List.of(1, 2)), Map.entry(4, List.of(6, 7)), Map.entry(5, List.of(6, 7)),
                        Map.entry(6, List.of(4, 5)), Map.entry(7, List.of(4, 5)), Map.entry(8, new ArrayList<>()));
            case WUF:
                yield Map.ofEntries(Map.entry(1, List.of(2, 6, 7)), Map.entry(2, List.of(1, 3, 7, 8)),
                        Map.entry(3, List.of(2, 5, 6, 8)), Map.entry(4, List.of(5, 7, 8)), Map.entry(5, List.of(3, 4, 6, 7)),
                        Map.entry(6, List.of(1, 3, 5, 7)), Map.entry(7, List.of(1, 2, 4, 5, 6, 8)), Map.entry(8, List.of(2, 3, 4, 7)));
            case CAKE:
                yield Map.ofEntries(Map.entry(1, List.of(2, 3, 6, 8)), Map.entry(2, List.of(1, 4, 6)),
                        Map.entry(3, List.of(1, 6, 7, 8)), Map.entry(4, List.of(2, 3, 5, 6, 7)), Map.entry(5, List.of(4, 7, 8)),
                        Map.entry(6, List.of(1, 2, 3, 4)), Map.entry(7, List.of(3, 4, 5, 8)), Map.entry(8, List.of(1, 3, 5, 7)));
            case QVN:
                yield Map.ofEntries(Map.entry(1, List.of(2, 4, 8)), Map.entry(2, List.of(1, 3, 6,7)),
                        Map.entry(3, List.of(2, 4, 6, 7)), Map.entry(4, List.of(1, 3, 5)), Map.entry(5, List.of(4, 6, 8)),
                        Map.entry(6, List.of(2, 3, 5,7)), Map.entry(7, List.of(2, 3, 6, 8)), Map.entry(8, List.of(1, 5, 7)));
        };
    }

    public boolean isConnected(int first, int second) {
        return nodes.get(first).contains(second);
    }

}
