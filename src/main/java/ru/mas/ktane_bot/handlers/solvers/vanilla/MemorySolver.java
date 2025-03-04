package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.Memory;

import java.util.Arrays;

import static ru.mas.ktane_bot.model.CommonValues.*;

@Component("memorySolver")
@RequiredArgsConstructor
public class MemorySolver implements Solver {

    private Memory module = new Memory();

    private final DataCache dataCache;
    
    private static final String PRESS = "Нажмите на ";

    @Override
    public MessageDto solve(String message, String userId) {
        var subState = dataCache.getUsersCurrentBotSubState(userId);
        if (dataCache.getUserModule(userId) != null)
            module = (Memory) dataCache.getUserModule(userId);
        var splitted = Arrays.stream(message.split("")).toList();
        switch (subState) {
            case MEMORY1:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY2);
                switch (splitted.get(0)) {
                    case ONE, TWO:
                        module = new Memory();
                        setValueAndPosition(userId, splitted.get(2), "2");
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(2)).build();
                    case THREE:
                        module = new Memory();
                        setValueAndPosition(userId, splitted.get(3), "3");
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(3)).build();
                    case FOUR:
                        module = new Memory();
                        setValueAndPosition(userId, splitted.get(4), "4");
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(4)).build();
                }
            case MEMORY2:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY3);
                switch (splitted.get(0)) {
                    case ONE:
                        setValueAndPosition(userId, "4", String.valueOf(splitted.subList(1, 5).indexOf("4") + 1));
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + FOUR).build();
                    case TWO, FOUR:
                        setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().getFirst())),
                                module.getPositions().getFirst());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(Integer.parseInt(module.getPositions().getFirst()))).build();
                    case THREE:
                        setValueAndPosition(userId, splitted.get(1), "1");
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(1)).build();
                }
            case MEMORY3:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY4);
                switch (splitted.get(0)) {
                    case ONE:
                        setValueAndPosition(userId, module.getValues().get(1),
                                String.valueOf(splitted.subList(1, 5).indexOf(module.getValues().get(1))));
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + module.getValues().get(1)).build();
                    case TWO:
                        setValueAndPosition(userId, module.getValues().get(0),
                                String.valueOf(splitted.subList(1, 5).indexOf(module.getValues().get(0))));
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + module.getValues().getFirst()).build();
                    case THREE:
                        setValueAndPosition(userId, splitted.get(3), "3");
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(3)).build();
                    case FOUR:
                        setValueAndPosition(userId, "4", String.valueOf(splitted.subList(1, 5).indexOf("4") + 1));
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + FOUR).build();
                }
            case MEMORY4:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY5);
                switch (splitted.get(0)) {
                    case ONE:
                        setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().getFirst())),
                                module.getPositions().getFirst());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(Integer.parseInt(module.getPositions().getFirst()))).build();
                    case TWO:
                        setValueAndPosition(userId, splitted.get(1), "1");
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(1)).build();
                    case THREE, FOUR:
                        setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().get(1))),
                                module.getPositions().get(1));
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + splitted.get(Integer.parseInt(module.getPositions().get(1)))).build();
                }
            case MEMORY5:
                dataCache.solveModule(userId);
                switch (splitted.getFirst()) {
                    case ONE:
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + module.getValues().get(0)).build();
                    case TWO:
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + module.getValues().get(1)).build();
                    case THREE:
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + module.getValues().get(3)).build();
                    case FOUR:
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(PRESS + module.getValues().get(2)).build();
                }
        }
        return null;
    }

    private void setValueAndPosition(String userId, String value, String position) {
        module.putPosition(position);
        module.putValue(value);
        dataCache.saveUserModule(userId, module);
    }
}
