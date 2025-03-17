package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.mods.introduction.SwitchesModule;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Component("switchesSolver")
@RequiredArgsConstructor
public class SwitchesSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var splitted = message.split(" ");
        var start = splitted[0].chars().mapToObj(c -> (char) c).map(c -> c.equals('d')).collect(Collectors.toList());
        var end = splitted[1].chars().mapToObj(c -> (char) c).map(c -> c.equals('d')).collect(Collectors.toList());
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(calculatePath(start, end)).build();
    }

    private String calculatePath(List<Boolean> start, List<Boolean> end) {
        var sj = new StringJoiner(" ");
        while (!start.equals(end)) {
            for (int i = 0; i < start.size(); i++) {
                if (start.get(i) != end.get(i)) {
                    var temp = new ArrayList<>(List.copyOf(start));
                    temp.set(i, !temp.get(i));
                    if (!SwitchesModule.exceptions.contains(temp)) {
                        start.set(i, !start.get(i));
                        sj.add(String.valueOf(i + 1));
                    }
                }
            }
        }
        return sj.toString();
    }
}
