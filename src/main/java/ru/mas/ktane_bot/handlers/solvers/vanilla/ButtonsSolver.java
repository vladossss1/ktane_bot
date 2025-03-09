package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.ButtonModule;

@Component("buttonsSolver")
@RequiredArgsConstructor
public class ButtonsSolver implements Solver {
    private static final String IMMEDIATE_RELEASE = "Нажмите и резко отпустите";
    private static final String HOLD_AND_RELEASE = "Удерживайте и если полоска синяя - отпустите на 4\nЕсли полоска жёлтая - отпустите на 5\nИначе отпустите на 1";

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var splitted = message.split(" ");
        var bomb = dataCache.getUserBomb(userId);
        var button = new ButtonModule(splitted[0], splitted[1]);
        dataCache.solveModule(userId);
        String result;
        if (button.getName().equalsIgnoreCase("abort")
                && button.getColor().equalsIgnoreCase("blue"))
            result = HOLD_AND_RELEASE;
        else if (button.getName().equalsIgnoreCase("detonate") && bomb.getBatteriesCount() > 1)
            result = IMMEDIATE_RELEASE;
        else if (button.getColor().equalsIgnoreCase("white")
                && bomb.getIndicators().stream().anyMatch(i -> i.getName().equals("CAR") && i.getLit()))
            result = HOLD_AND_RELEASE;
        else if (bomb.getBatteriesCount() > 2
                && bomb.getIndicators().stream().anyMatch(i -> i.getName().equals("FRK") && i.getLit()))
            result = IMMEDIATE_RELEASE;
        else if (button.getColor().equalsIgnoreCase("yellow"))
            result = HOLD_AND_RELEASE;
        else if (button.getColor().equalsIgnoreCase("red") && button.getName().equalsIgnoreCase("hold"))
            result = IMMEDIATE_RELEASE;
        else
            result = HOLD_AND_RELEASE;

        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
