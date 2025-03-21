package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.WhosOnFirstModule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component("whosOnFirstSolver")
@RequiredArgsConstructor
public class WhosOnFirstSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var splitted = Arrays.stream(message.split(",")).toList();
        var module = (WhosOnFirstModule) dataCache.getUserModule(userId);
        String result = "";
        for (var word : WhosOnFirstModule.stepTwo.get(splitted.get(WhosOnFirstModule.stepOne.get(splitted.get(0))))) {
            if (splitted.subList(1, 7).contains(word)) {
                result = word;
                break;
            }
        }
        if (module.getStage() == 2)
            dataCache.solveModule(userId);
        else
            module.nextStage();

        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
