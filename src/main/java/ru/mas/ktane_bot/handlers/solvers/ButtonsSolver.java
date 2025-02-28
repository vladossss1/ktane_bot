package ru.mas.ktane_bot.handlers.solvers;

import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.Handler;
import ru.mas.ktane_bot.model.modules.Button;

@Component
public class ButtonsSolver extends Handler {
    private static final String IMMEDIATE_RELEASE = "Нажмите и резко отпустите";
    private static final String HOLD_AND_RELEASE = "Удерживайте и если полоска синяя - отпустите на 4\nЕсли полоска жёлтая - отпустите на 5\nИначе отпустите на 1";

    public ButtonsSolver(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var splitted = message.split(" ");
        var bomb = userDataCache.getUserBomb(userId);
        var button = new Button(splitted[0], splitted[1]);
        userDataCache.solveModule(userId);
        if (button.getName().equalsIgnoreCase("abort")
                && button.getColor().equalsIgnoreCase("blue"))
            return HOLD_AND_RELEASE;
        else if (button.getName().equalsIgnoreCase("detonate") && bomb.getBatteriesCount() > 1)
            return IMMEDIATE_RELEASE;
        else if (button.getColor().equalsIgnoreCase("white")
                && bomb.getIndicators().stream().anyMatch(i -> i.getName().equals("CAR") && i.getLit()))
            return HOLD_AND_RELEASE;
        else if (bomb.getBatteriesCount() > 2
                && bomb.getIndicators().stream().anyMatch(i -> i.getName().equals("FRK") && i.getLit()))
            return IMMEDIATE_RELEASE;
        else if (button.getColor().equalsIgnoreCase("yellow"))
            return HOLD_AND_RELEASE;
        else if (button.getColor().equalsIgnoreCase("red") && button.getName().equalsIgnoreCase("hold"))
            return IMMEDIATE_RELEASE;
        else
            return HOLD_AND_RELEASE;
    }
}
