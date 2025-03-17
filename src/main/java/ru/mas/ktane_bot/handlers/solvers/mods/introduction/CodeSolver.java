package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;

@Component("codeSolver")
@RequiredArgsConstructor
public class CodeSolver implements Solver {

    private final DataCache dataCache;
    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        int result;
        int parsedMessage = Integer.parseInt(message);
        if (bomb.getFirstDigit() == bomb.getLastDigit() && bomb.getBatteriesCount() == 0)
            result = parsedMessage;
        else if (bomb.hasIndicator("CLR"))
            result = parsedMessage / 8;
        else if (bomb.serialHasSymbol("[xyz]"))
            result = parsedMessage / 20;
        else if (bomb.getPorts().size() > 4)
            result = parsedMessage / 30;
        else if (bomb.getBatteriesCount() == 0)
            result = parsedMessage / 42;
        else if (bomb.getIndicatorCount(true) > bomb.getIndicatorCount(false))
            result = parsedMessage / 69;
        else
            result = parsedMessage / 3;
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(String.valueOf(Math.abs(result))).build();
    }
}
