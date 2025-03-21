package ru.mas.ktane_bot.handlers.solvers.vanilla.needy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.needy.KnobsModule;

import java.util.Arrays;

@Component("knobsSolver")
@RequiredArgsConstructor
public class KnobsSolver implements Solver {

    @Override
    public MessageDto solve(String message, String userId) {
        var splitted = Arrays.stream(message.split(" ")).toList();
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(KnobsModule.positions.get(splitted.get(1))).build();
    }
}
