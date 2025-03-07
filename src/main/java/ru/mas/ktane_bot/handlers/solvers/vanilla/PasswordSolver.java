package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.PasswordModule;

@Component("passwordSolver")
@RequiredArgsConstructor
public class PasswordSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var letters = message.chars().mapToObj(c -> (char) c).toList();
        var module = (PasswordModule) dataCache.getUserModule(userId);
        module.setCurrentWords(module.getCurrentWords().stream().filter(w -> letters.contains(w.toCharArray()[module.getAndIncrementStage()])).toList());
        if (module.getCurrentWords().size() == 1) {
            dataCache.solveModule(userId);
            return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getCurrentWords().get(0)).build();
        } else {
            dataCache.saveUserModule(userId, module);
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }
    }
}
