package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;

import java.util.List;

@Component("emojiMathSolver")
@RequiredArgsConstructor
public class EmojiMathSolver implements Solver {

    private final DataCache dataCache;

    private static final List<String> emoji = List.of(
            ":)",
            "=(",
            "(:",
            ")=",
            ":(",
            "):",
            "=)",
            "(=",
            ":|",
            "|:"
    );

    @Override
    public MessageDto solve(String message, String userId) {
        dataCache.solveModule(userId);
        var result = new StringBuilder();
        var chars = message.toCharArray();
        for (int i = 0; i < chars.length; i += 2) {
            if (chars[i] == '+' || chars[i] == '-')
                result.append(chars[i++]);
            result.append(emoji.indexOf(chars[i] + String.valueOf(chars[i + 1])));
        }
        var splitted = result.toString().split("[+\\-]");
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(String.valueOf(message.contains("+") ? Integer.parseInt(splitted[0]) + Integer.parseInt(splitted[1]) :
                Integer.parseInt(splitted[0]) - Integer.parseInt(splitted[1]))).build();
    }
}
