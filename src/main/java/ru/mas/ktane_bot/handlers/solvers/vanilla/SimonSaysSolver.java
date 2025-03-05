package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.SimonSaysModule;

import java.util.Map;

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
        var module = (SimonSaysModule) dataCache.getUserModule(userId);
        if (message.equals("stop")) {
            dataCache.solveModule(userId);
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }
        switch (bomb.getErrorsCount()) {
            case 0:
                calculate(message, userId, module, bomb.serialHasVowel() ?
                        Map.of("r", BLUE, "b", RED, "g", YELLOW, "y", GREEN) :
                        Map.of("r", BLUE, "b", YELLOW, "g", GREEN, "y", RED));
                break;
            case 1:
                calculate(message, userId, module, bomb.serialHasVowel() ?
                        Map.of("r", YELLOW, "b", GREEN, "g", BLUE, "y", RED) :
                        Map.of("r", RED, "b", BLUE, "g", YELLOW, "y", GREEN));
                break;
            default:
                calculate(message, userId, module, bomb.serialHasVowel() ?
                        Map.of("r", GREEN, "b", RED, "g", YELLOW, "y", BLUE) :
                        Map.of("r", YELLOW, "b", GREEN, "g", BLUE, "y", RED));
                break;
        }
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(module.getResult()).build();
    }

    private void calculate(String message, String userId, SimonSaysModule module, Map<String, String> colors) {
        switch (message) {
            case "r":
                module.setResult(module.getResult() + colors.get("r") + " ");
                dataCache.saveUserModule(userId, module);
                break;
            case "b":
                module.setResult(module.getResult() + colors.get("b") + " ");
                dataCache.saveUserModule(userId, module);
                break;
            case "g":
                module.setResult(module.getResult() + colors.get("g") + " ");
                dataCache.saveUserModule(userId, module);
                break;
            case "y":
                module.setResult(module.getResult() + colors.get("y") + " ");
                dataCache.saveUserModule(userId, module);
                break;
        }

    }
}
