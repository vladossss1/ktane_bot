package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.astrology.AstrologySymbol;
import ru.mas.ktane_bot.model.modules.mods.introduction.AstrologyModule;

import static ru.mas.ktane_bot.model.CommonValues.PRESS;
import static ru.mas.ktane_bot.model.CommonValues.WHEN_ON_TIMER;

@Component("astrologySolver")
@RequiredArgsConstructor
public class AstrologySolver implements Solver {

    private final DataCache dataCache;

    private final static String NO_OMEN = "NO OMEN";
    private final static String GOOD_OMEN = "GOOD OMEN";
    private final static String POOR_OMEN = "POOR OMEN";
    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        var module = (AstrologyModule) dataCache.getUserModule(userId);
        module.addSymbol(AstrologySymbol.getByStickerId(message));
        dataCache.saveUserModule(userId, module);
        if (module.getSymbols().size() < 3) {
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }
        var resultNumber = module.getTableValue();
        for (var symbol: module.getSymbols())
            resultNumber += bomb.serialHasSymbol("[" + symbol.name() + "]") ? 1 : -1;
        var result = PRESS + (resultNumber == 0 ? NO_OMEN :
                (resultNumber > 0 ? GOOD_OMEN : POOR_OMEN) + ", " + WHEN_ON_TIMER + Math.abs(resultNumber));
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
