package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.mods.introduction.CrazyTalkModule;

@Component("crazyTalkSolver")
@RequiredArgsConstructor
public class CrazyTalkSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var module = (CrazyTalkModule) dataCache.getUserModule(userId);
        String result;
        if (message.equals("_")) {
            dataCache.solveModule(userId);
            result = CrazyTalkModule.wordMap.get(module.getCurrentWords().get(module.getCurrentWords().indexOf(module.getCurrentResult())));
        } else {
            if (module.getCurrentWords().isEmpty()) {
                module.setCurrentWords(CrazyTalkModule.wordMap.keySet().stream().filter(w -> w.startsWith(message.toUpperCase())).toList());
                module.setCurrentResult(message.toUpperCase());
            } else {
                module.setCurrentResult(module.getCurrentResult() + " " + message.toUpperCase());
                module.setCurrentWords(module.getCurrentWords().stream().filter(w -> w.startsWith(module.getCurrentResult())).toList());
            }
            if (module.getCurrentWords().size() == 1) {
                dataCache.solveModule(userId);
                result = CrazyTalkModule.wordMap.get(module.getCurrentWords().get(0));
            } else {
                dataCache.saveUserModule(userId, module);
                return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
            }
        }
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
