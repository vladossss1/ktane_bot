package ru.mas.ktane_bot.handlers.solvers.vanilla.needy;

import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;

import java.util.Arrays;
import java.util.Map;

public class KnobsSolver extends Handler {

    private static final String UP = "Вверх";
    private static final String DOWN = "Вниз";
    private static final String LEFT = "Влево";
    private static final String RIGHT = "Вправо";


    private static final Map<String, String> result = Map.ofEntries(
            Map.entry("35612346", UP), Map.entry("1352356", UP),
            Map.entry("23612346", DOWN), Map.entry("13526", DOWN),
            Map.entry("51456", LEFT), Map.entry("545", LEFT),
            Map.entry("134561235", RIGHT), Map.entry("1341235", RIGHT)
    );
    public KnobsSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var splitted = Arrays.stream(message.split(" ")).toList();
        return result.get(splitted.get(1));
    }
}
