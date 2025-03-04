package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;

import java.util.regex.Pattern;

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
        if (number == 69)
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(D).build();
        else if (number % 6 == 0)
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(A).build();
        else if (number % 3 == 0 && bomb.getBatteriesCount() > 1)
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(B).build();
        else if (Pattern.compile("[ce3]", Pattern.CASE_INSENSITIVE).matcher(bomb.getSerialNumber()).find()) {
            if (number > 21 && number < 80)
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(B).build();
            else
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(C).build();
        }
        else if (number < 46)
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(D).build();
        else
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(A).build();
    }
}
