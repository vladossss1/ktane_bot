package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.KeyboardModule;

import java.util.ArrayList;
import java.util.Comparator;

@Component("keyboardSolver")
@RequiredArgsConstructor
public class KeyboardSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var module = (KeyboardModule) dataCache.getUserModule(userId);
        module.addSticker(message);
        dataCache.saveUserModule(userId, module);
        if (module.getStickers().size() < 4) {
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }
        var buttons = module.getStickers();
        for (var column : KeyboardModule.table) {
            if (column.containsAll(buttons)) {
                buttons.sort(Comparator.comparingInt(column::indexOf));
                dataCache.solveModule(userId);
                var sb = new ArrayList<InputFile>();
                buttons.forEach(b -> sb.add(new InputFile(b)));
                return MessageDto.builder().messageType(MessageType.STICKER_LIST).userId(userId).stickers(sb).build();
            }
        }
        return null;
    }
}
