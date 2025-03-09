package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.MorseModule;

import java.util.Map;

import static ru.mas.ktane_bot.model.modules.vanilla.MorseModule.letters;
import static ru.mas.ktane_bot.model.modules.vanilla.MorseModule.words;

@Component("morseSolver")
@RequiredArgsConstructor
public class MorseSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var letter = letters.get(message);
        var module = (MorseModule) dataCache.getUserModule(userId);
        module.addLetter(letter);
        var result = module.getResult();
        var resultList = words.keySet().stream().filter(w -> w.startsWith(result)).map(words::get).toList();
        if (resultList.size() > 1) {
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        } else {
            dataCache.solveModule(userId);
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(resultList.get(0)).build();
        }
    }
}
