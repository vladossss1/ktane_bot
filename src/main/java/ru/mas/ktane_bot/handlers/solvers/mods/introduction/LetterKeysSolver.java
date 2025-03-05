package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;

import static ru.mas.ktane_bot.model.CommonValues.*;

@Component("letterKeysSolver")
@RequiredArgsConstructor
public class LetterKeysSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        dataCache.solveModule(userId);
        var number = Integer.parseInt(message);
        var bomb = dataCache.getUserBomb(userId);
        String result;
        if (number == 69)
            result = D;
        else if (number % 6 == 0)
            result = A;
        else if (number % 3 == 0 && bomb.getBatteriesCount() > 1)
            result = B;
        else if (bomb.serialHasSymbol("[ce3]"))
            result = number > 21 && number < 80 ? B : C;
        else if (number < 46)
            result = D;
        else
            result = A;

        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
