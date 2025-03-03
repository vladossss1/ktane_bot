package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.modules.Memory;

import java.util.Arrays;

import static ru.mas.ktane_bot.model.modules.CommonValues.*;

@Component("memorySolver")
@RequiredArgsConstructor
public class MemorySolver implements Solver {

    private Memory module = new Memory();

    private final DataCache dataCache;

    @Override
    public String solve(String message, Long userId) {
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
                        return "Нажмите на " + splitted.get(2);
                    case THREE:
                        module = new Memory();
                        setValueAndPosition(userId, splitted.get(3), "3");
                        return "Нажмите на " + splitted.get(3);
                    case FOUR:
                        module = new Memory();
                        setValueAndPosition(userId, splitted.get(4), "4");
                        return "Нажмите на " + splitted.get(4);
                }
            case MEMORY2:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY3);
                switch (splitted.get(0)) {
                    case ONE:
                        setValueAndPosition(userId, "4", String.valueOf(splitted.subList(1, 5).indexOf("4") + 1));
                        return "Нажмите на " + FOUR;
                    case TWO, FOUR:
                        setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().getFirst())),
                                module.getPositions().getFirst());
                        return "Нажмите на " + splitted.get(Integer.parseInt(module.getPositions().getFirst()));
                    case THREE:
                        setValueAndPosition(userId, splitted.get(1), "1");
                        return "Нажмите на " + splitted.get(1);
                }
            case MEMORY3:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY4);
                switch (splitted.get(0)) {
                    case ONE:
                        setValueAndPosition(userId, module.getValues().get(1),
                                String.valueOf(splitted.subList(1, 5).indexOf(module.getValues().get(1))));
                        return "Нажмите на " + module.getValues().get(1);
                    case TWO:
                        setValueAndPosition(userId, module.getValues().get(0),
                                String.valueOf(splitted.subList(1, 5).indexOf(module.getValues().get(0))));
                        return "Нажмите на " + module.getValues().getFirst();
                    case THREE:
                        setValueAndPosition(userId, splitted.get(3), "3");
                        return "Нажмите на " + splitted.get(3);
                    case FOUR:
                        setValueAndPosition(userId, "4", String.valueOf(splitted.subList(1, 5).indexOf("4") + 1));
                        return "Нажмите на " + FOUR;
                }
            case MEMORY4:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY5);
                switch (splitted.get(0)) {
                    case ONE:
                        setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().getFirst())),
                                module.getPositions().getFirst());
                        return "Нажмите на " + splitted.get(Integer.parseInt(module.getPositions().getFirst()));
                    case TWO:
                        setValueAndPosition(userId, splitted.get(1), "1");
                        return "Нажмите на " + splitted.get(1);
                    case THREE, FOUR:
                        setValueAndPosition(userId, splitted.get(Integer.parseInt(module.getPositions().get(1))),
                                module.getPositions().get(1));
                        return "Нажмите на " + splitted.get(Integer.parseInt(module.getPositions().get(1)));
                }
            case MEMORY5:
                dataCache.solveModule(userId);
                switch (splitted.getFirst()) {
                    case ONE:
                        return "Нажмите на " + module.getValues().get(0);
                    case TWO:
                        return "Нажмите на " + module.getValues().get(1);
                    case THREE:
                        return "Нажмите на " + module.getValues().get(3);
                    case FOUR:
                        return "Нажмите на " + module.getValues().get(2);
                }
        }
        return null;
    }

    private void setValueAndPosition(Long userId, String value, String position) {
        module.putPosition(position);
        module.putValue(value);
        dataCache.saveUserModule(userId, module);
    }
}
