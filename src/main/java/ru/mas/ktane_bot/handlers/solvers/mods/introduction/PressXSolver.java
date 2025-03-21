package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.mods.introduction.PressXModule;

import static ru.mas.ktane_bot.model.CommonValues.*;

@Component("pressXSolver")
@RequiredArgsConstructor
public class PressXSolver implements Solver {

    private final DataCache dataCache;
    private final static String ANY_TIME = " в любое время";

    private final static String ANY_BUTTON = " любую кнопку";

    private final static String LAST_DIGIT = " последней цифрой";

    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        var button = PressXModule.buttonsMap.get(bomb.getSolvedModulesCount() % 4)
                .get(bomb.getIndicatorCount(true) < bomb.getIndicatorCount(false) ? 0 :
                        bomb.getIndicatorCount(true) > bomb.getIndicatorCount(false) ? 1 : 2);
        var result = "";
        if (button.equals(X) && bomb.hasIndicator("CAR", true) && bomb.getBatteriesCount() < 2)
            result = PRESS + ANY_BUTTON + ANY_TIME;
        else if (bomb.getBatteriesCount() > 2)
            result = PRESS + button + ", " + WHEN_ON_TIMER + bomb.getFirstDigit() + LAST_DIGIT;
        else if (button.equals(A) && bomb.serialHasSymbol("[25]"))
            result = PRESS + button + ", " + WHEN_ON_TIMER + "05 или 30";
        else if (!button.equals(Y) && bomb.hasIndicator("NSA", true))
            result = PRESS + button + ", " + WHEN_ON_TIMER + "одна цифра равна другой";
        else
            result = PRESS + button + ", " + "сумма цифр секунд равна 9";
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
