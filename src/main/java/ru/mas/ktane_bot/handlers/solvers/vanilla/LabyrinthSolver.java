package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.vanilla.LabyrinthModule;
import ru.mas.ktane_bot.model.modules.labyrinth.LabyrinthVertex;

import java.util.*;

import static ru.mas.ktane_bot.model.CommonValues.*;
import static ru.mas.ktane_bot.model.modules.vanilla.LabyrinthModule.circles;

@Component("labyrinthSolver")
@RequiredArgsConstructor
public class LabyrinthSolver implements Solver {

    private final DataCache dataCache;

    @Override
    public MessageDto solve(String message, String userId) {
        var splitted = Arrays.stream(message.split(" ")).toList();
        var module = (LabyrinthModule) dataCache.getUserModule(userId);
        module.loadLabyrinth(splitted.get(0), splitted.get(1), splitted.get(2), splitted.get(3));
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(buildPath(module.getPath())).build();
    }

    private String buildPath(LinkedList<LabyrinthVertex> path) {
        var sb = new StringBuilder();
        for (int i = 1; i < path.size(); i++) {
            if (path.get(i).getCoordinate() - path.get(i - 1).getCoordinate() == 6)
                sb.append(DOWN).append(NEW_ROW);
            else if (path.get(i).getCoordinate() - path.get(i - 1).getCoordinate() == -6)
                sb.append(UP).append(NEW_ROW);
            else if (path.get(i).getCoordinate() - path.get(i - 1).getCoordinate() == -1)
                sb.append(LEFT).append(NEW_ROW);
            else
                sb.append(RIGHT).append(NEW_ROW);
        }
        return sb.toString();
    }
}
