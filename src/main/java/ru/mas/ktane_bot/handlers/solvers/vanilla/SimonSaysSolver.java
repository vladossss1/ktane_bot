package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.SimonSays;

@Component("simonSaysSolver")
@RequiredArgsConstructor
public class SimonSaysSolver implements Solver {

    private final DataCache dataCache;

    private static final String BLUE = "Синяя";
    private static final String RED = "Красная";
    private static final String GREEN = "Зелёная";
    private static final String YELLOW = "Жёлтая";

    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        if (dataCache.getUserModule(userId) == null)
            dataCache.saveUserModule(userId, new SimonSays());
        var module = (SimonSays) dataCache.getUserModule(userId);
        if (message.equals("stop")) {
            dataCache.solveModule(userId);
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }
        if (bomb.serialHasVowel()) {
            switch (bomb.getErrorsCount()) {
                case 0:
                    if (calculate(message, userId, module, BLUE, RED, YELLOW, GREEN)) return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getResult()).build();
                case 1:
                    if (calculate(message, userId, module, YELLOW, GREEN, BLUE, RED)) return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getResult()).build();
                case 2:
                    if (calculate(message, userId, module, GREEN, RED, YELLOW, BLUE)) return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getResult()).build();
            }
        }
        else {
            switch (bomb.getErrorsCount()) {
                case 0:
                    if (calculate(message, userId, module, BLUE, YELLOW, GREEN, RED)) return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getResult()).build();
                case 1:
                    if (calculate(message, userId, module, RED, BLUE, YELLOW, GREEN)) return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getResult()).build();
                case 2:
                    if (calculate(message, userId, module, YELLOW, GREEN, BLUE, RED)) return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getResult()).build();
            }
        }
        return null;
    }

    private boolean calculate(String message, String userId, SimonSays module, String r, String b, String g, String y) {
        switch (message) {
            case "r":
                module.setResult(module.getResult() + r + " ");
                dataCache.saveUserModule(userId, module);
                return true;
            case "b":
                module.setResult(module.getResult() + b + " ");
                dataCache.saveUserModule(userId, module);
                return true;
            case "g":
                module.setResult(module.getResult() + g + " ");
                dataCache.saveUserModule(userId, module);
                return true;
            case "y":
                module.setResult(module.getResult() + y + " ");
                dataCache.saveUserModule(userId, module);
                return true;
        }
        return false;
    }
}
