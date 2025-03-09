package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.bomb.PortType;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.mods.introduction.TwoBitsModule;

@Component("twoBitsSolver")
@RequiredArgsConstructor
public class TwoBitsSolver implements Solver {

    private final DataCache dataCache;

    private static final String SUBMIT = "submit";
    private static final String QUERY = "query";

    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        var module = (TwoBitsModule) dataCache.getUserModule(userId);
        var result = switch (module.getStage()) {
            case 0:
                module.nextStage();
                var temp = 0;
                if (bomb.serialHasSymbol(".*[a-z].*"))
                    temp += bomb.getFirstLetter() - 'A' + 1;
                temp += bomb.getLastDigit() * bomb.getBatteriesCount();
                if (bomb.hasPort(PortType.STEREO_RCA) && !bomb.hasPort(PortType.RJ_45))
                    temp *= 2;
                var stringTemp = String.valueOf(temp);
                if (temp > 99) {
                    var tempSubString = stringTemp.substring(stringTemp.length() - 2);
                    yield module.getMatrix().get(tempSubString.charAt(0) - '0').get(tempSubString.charAt(1) - '0') + " " + QUERY;
                } else if (temp < 10)
                    yield module.getMatrix().get(0).get(stringTemp.charAt(0) - '0') + " " + QUERY;
                else
                    yield module.getMatrix().get(stringTemp.charAt(0) - '0').get(stringTemp.charAt(1) - '0') + " " + QUERY;
            case 3:
                dataCache.solveModule(userId);
                yield module.getMatrix().get(message.charAt(0) - '0').get(message.charAt(1) - '0') + " " + SUBMIT;
            default:
                module.nextStage();
                yield module.getMatrix().get(message.charAt(0) - '0').get(message.charAt(1) - '0') + " " + QUERY;
        };
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
