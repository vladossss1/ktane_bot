package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.PortType;
import ru.mas.ktane_bot.model.modules.mods.introduction.PianoKeysModule;

import java.util.List;

import static ru.mas.ktane_bot.model.CommonValues.*;

@Component("pianoKeysSolver")
@RequiredArgsConstructor
public class PianoKeysSolver implements Solver {

    private final DataCache dataCache;

    private static final String FLAT = "CAACAgIAAxkBAAIfHGfHGMv1c3bZqCjrG4ZXt_JNPkUwAAJGZQAC2MA4SrRQW8DbfvBQNgQ";
    private static final String FERMATA = "CAACAgIAAxkBAAIfHWfHGM0gVOkDqiOLTRwNtfQMkvSxAAIfagACrp04SoNrnhYr4JVvNgQ";
    private static final String CLEF = "CAACAgIAAxkBAAIfHmfHGNPs5KSsKkT4G8Y_5apzVKwBAALWagACdeQ5SqUP9OIyOveKNgQ";
    private static final String MORDENT = "CAACAgIAAxkBAAIfH2fHGNXwBZSkH0rEcMYlklelU6_RAALdZwAC0IU4Si_zO1Hylv6gNgQ";
    private static final String NATURAL = "CAACAgIAAxkBAAIfIGfHGNXwb4LsvIvtD5xMKtP7ZAPTAAKBZgACWAI5Srr5UxdQsN8QNgQ";
    private static final String SHARP = "CAACAgIAAxkBAAIfIWfHGNWkfVORyx4UYZpxEaVBY6YFAALvbQACO2U5Soms2VT40Kx7NgQ";
    private static final String TURN = "CAACAgIAAxkBAAIfImfHGNiV7j_O8v8LeC3kUG2MN8cNAALTZwACXyQ4Svs87rtVqGsgNgQ";
    private static final String COMMON_TIME = "CAACAgIAAxkBAAIfI2fHGNqhSxO3vF8r23PmmwHS3ExyAAIdZwACijRASvaR6ed0BCdKNgQ";
    private static final String CUT_TIME = "CAACAgIAAxkBAAIfJGfHGNvsvZBw-2kQUDtUm0Agd7d4AALGawACz6E5Sg5qcHXjEGsWNgQ";
    private static final String BLACK = "Чёрная";
    private static final String WHITE = "Белая";
    private static final String C = ONE + " " + WHITE;
    private static final String D = TWO + " " + WHITE;
    private static final String E = THREE + " " + WHITE;
    private static final String F = FOUR + " " + WHITE;
    private static final String G = FIVE + " " + WHITE;
    private static final String A = SIX + " " + WHITE;
    private static final String B = SEVEN + " " + WHITE;
    private static final String Db = ONE + " " + BLACK;
    private static final String Eb = TWO + " " + BLACK;
    private static final String Gb = THREE + " " + BLACK;
    private static final String Ab = FOUR + " " + BLACK;
    private static final String Bb = FIVE + " " + BLACK;

    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        var module = (PianoKeysModule) dataCache.getUserModule(userId);
        var result = "";
        module.addSymbol(message);
        if (module.getSymbols().size() < 3) {
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }
        if (module.hasSymbol(FLAT) && bomb.isLastDigitOfSerialNumberEven())
            result = String.join("\n", Bb, Bb, Bb, Bb, Gb, Ab, Bb, Ab, Bb);
        else if (module.hasOneOfSymbols(COMMON_TIME + "|" + SHARP) && bomb.getBatteriesHoldersCount() > 1)
            result = String.join("\n", Eb, Eb, D, D, Eb, Eb, D, Eb, Eb, D, D, Eb);
        else if (module.hasAllSymbols(List.of(NATURAL, FERMATA)))
            result = String.join("\n", E, Gb, Gb, Gb, Gb, E, E, E);
        else if (module.hasOneOfSymbols(CUT_TIME + "|" + TURN) && bomb.getPorts().contains(PortType.STEREO_RCA))
            result = String.join("\n", Bb, A, Bb, F, Eb, Bb, A, Bb, F, Eb);
        else if (module.hasSymbol(CLEF) && bomb.hasIndicator("SND", true))
            result = String.join("\n", E, E, E, C, E, G, G);
        else if (module.hasOneOfSymbols(MORDENT + "|" + FERMATA + "|" + COMMON_TIME) && bomb.getBatteriesCount() > 2)
            result = String.join("\n", Db, D, E, F, Db, D, E, F, Bb, A);
        else if (module.hasAllSymbols(List.of(FLAT, SHARP)))
            result = String.join("\n", G, G, C, G, G, C, G, C);
        else if (module.hasOneOfSymbols(CUT_TIME + "|" + MORDENT) && bomb.serialHasSymbol("[378]"))
            result = String.join("\n", A, E, F, G, F, E, D, D, F, A);
        else if (module.hasOneOfSymbols(NATURAL + "|" + TURN + "|" + CLEF))
            result = String.join("\n", G, G, G, Eb, Bb, G, Eb, Bb, G);
        else
            result = String.join("\n", B, D, A, G, A, B, D, A);

        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
