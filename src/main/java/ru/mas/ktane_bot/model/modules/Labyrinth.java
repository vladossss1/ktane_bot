package ru.mas.ktane_bot.model.modules;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Labyrinth extends BombModule {
    private int destination;
    private int start;
    List<LabyrinthVertex> table;

    public boolean getPath(LabyrinthVertex start, LabyrinthVertex end, HashSet<LabyrinthVertex> passed, LinkedList<LabyrinthVertex> path) {
        passed.add(start);
        if (start.equals(end)){
            path.addFirst(start);
            return true;
        }
        for (var vertex: start.getNeighbors()) {
            if (!passed.contains(vertex)) {
                if (getPath(vertex, end, passed, path)) {
                    path.addFirst(start);
                    return true;
                }
            }
        }
        return false;
    }
}
