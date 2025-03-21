package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.mods.introduction.ShapeShiftModule;
import ru.mas.ktane_bot.model.modules.shapeshift.Shape;
import ru.mas.ktane_bot.model.modules.shapeshift.ShapeType;

@Component("shapeShiftSolver")
@RequiredArgsConstructor
public class ShapeShiftSolver implements Solver {

    private final DataCache dataCache;

    private final static String LEFT = "Слева ";
    private final static String RIGHT = "Справа ";
    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        var module = (ShapeShiftModule) dataCache.getUserModule(userId);
        module.fillShapes(bomb);
        var splitted = message.split(" ");
        var leftSide = ShapeType.getByName(splitted[0]);
        var rightSide = ShapeType.getByName(splitted[1]);
        var current = module.getShapes().stream().filter(s -> s.equals(new Shape(leftSide, rightSide))).findFirst().get();
        while (!current.isBeen()) {
            current.setBeen(true);
            current = current.getNext();
        }
        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                .text(LEFT + current.getLeftSide().getAnswer() + " " + RIGHT + current.getRightSide().getAnswer()).build();
    }
}
