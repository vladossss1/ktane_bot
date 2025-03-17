package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.listening.ListeningButtons;

@Component("listeningSolver")
@RequiredArgsConstructor
public class ListeningSolver implements Solver {

    private final DataCache dataCache;
    @Override
    public MessageDto solve(String message, String userId) {
        var module = dataCache.getUserModule(userId);
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.EDIT_TEXT).userId(userId).text(ListeningButtons.getAnswerByName(message))
                .messageId(module.getMessageWithKeyboardId()).build();
    }
}
