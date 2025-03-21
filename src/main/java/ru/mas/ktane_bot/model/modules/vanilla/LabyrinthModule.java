package ru.mas.ktane_bot.model.modules.vanilla;

import lombok.Getter;
import lombok.Setter;
import ru.mas.ktane_bot.model.modules.labyrinth.LabyrinthVertex;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.*;

@Getter
@Setter
public class LabyrinthModule extends BombModule {
    public static final List<List<Integer>> circles = List.of(
            List.of(6, 17),
            List.of(19, 10),
            List.of(21, 23),
            List.of(0, 18),
            List.of(33, 16),
            List.of(26, 4),
            List.of(1, 31),
            List.of(20, 3),
            List.of(24, 8)
    );

    private int destination;
    private int start;
    List<LabyrinthVertex> table;

    private ArrayList<Integer> getNeighbors(int i, HashSet<Set<Integer>> walls) {
        var neighbors = new ArrayList<Integer>();
        var row = i / 6;
        var col = i % 6;

        var moves = new int[]{-1, 1, -6, 6};
        for (var move : moves) {
            var neighbor = i + move;
            var neighborRow = neighbor / 6;
            var neighborCol = neighbor % 6;

            if (neighbor >= 0 && neighbor < 36 && Math.abs(neighborRow - row) <= 1 && Math.abs(neighborCol - col) <= 1) {
                var wallCheck = new HashSet<>(Set.of(i, neighbor));
                if (!walls.contains(wallCheck)) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    public void loadLabyrinth(String firstCircle, String secondCircle, String start, String destination) {
        setStart(getNumericValue(start.charAt(0), start.charAt(1)));
        setDestination(getNumericValue(destination.charAt(0), destination.charAt(1)));
        var numericFirstCircle = getNumericValue(firstCircle.charAt(0), firstCircle.charAt(1));
        var numericSecondCircle = getNumericValue(secondCircle.charAt(0), secondCircle.charAt(1));
        var walls = new HashSet<Set<Integer>>();
        for (int i = 0; i < circles.size(); i++) {
            if (circles.get(i).containsAll(List.of(numericFirstCircle, numericSecondCircle))) {
                walls = switch (i) {
                    case 0:
                        yield loadFirstLabyrinth();
                    case 1:
                        yield loadSecondLabyrinth();
                    case 2:
                        yield loadThirdLabyrinth();
                    case 3:
                        yield loadFourthLabyrinth();
                    case 4:
                        yield loadFifthLabyrinth();
                    case 5:
                        yield loadSixthLabyrinth();
                    case 6:
                        yield loadSeventhLabyrinth();
                    case 7:
                        yield loadEighthLabyrinth();
                    case 8:
                        yield loadNinthLabyrinth();
                    default:
                        throw new IllegalStateException("Unexpected value: " + i);
                };
                break;
            }
        }

        var vertexes = setDefaultVertexes();
        for (int i = 0; i < 36; i++) {
            var neighbors = getNeighbors(i, walls);

            vertexes.get(i).setNeighbors(neighbors.stream().map(n -> vertexes.stream().filter(v -> n.equals(v.getCoordinate())).findFirst().get()).toList());
        }
        setTable(vertexes);
    }

    private HashSet<Set<Integer>> loadFirstLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(1, 7), Set.of(2, 3), Set.of(4, 10), Set.of(5, 11), Set.of(6, 7),
                Set.of(8, 14), Set.of(8, 9), Set.of(9, 15), Set.of(10, 16), Set.of(12, 13),
                Set.of(13, 19), Set.of(14, 15), Set.of(16, 22), Set.of(18, 19), Set.of(19, 25),
                Set.of(20, 26), Set.of(21, 22), Set.of(21, 27), Set.of(22, 28),
                Set.of(25, 31), Set.of(26, 27), Set.of(28, 29), Set.of(28, 34), Set.of(31, 32), Set.of(33, 34)));
    }

    private HashSet<Set<Integer>> loadSecondLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(0, 6), Set.of(2, 3), Set.of(2, 8), Set.of(7, 8), Set.of(9, 10),
                Set.of(5, 11), Set.of(7, 13), Set.of(9, 15), Set.of(10, 16), Set.of(12, 13),
                Set.of(14, 15), Set.of(14, 20), Set.of(16, 22), Set.of(19, 20), Set.of(21, 22),
                Set.of(21, 27), Set.of(19, 25), Set.of(22, 23), Set.of(24, 25), Set.of(25, 26),
                Set.of(26, 27), Set.of(28, 29), Set.of(28, 34), Set.of(30, 31), Set.of(32, 33)));
    }

    private HashSet<Set<Integer>> loadThirdLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(1, 7), Set.of(2, 3), Set.of(3, 4), Set.of(6, 12), Set.of(6, 7),
                Set.of(7, 8), Set.of(8, 9), Set.of(9, 15), Set.of(10, 16), Set.of(10, 11),
                Set.of(13, 14), Set.of(14, 15), Set.of(16, 17), Set.of(18, 19), Set.of(19, 20),
                Set.of(20, 21), Set.of(21, 22), Set.of(22, 23), Set.of(24, 25), Set.of(26, 27),
                Set.of(27, 28), Set.of(28, 29), Set.of(33, 34)));
    }

    private HashSet<Set<Integer>> loadFourthLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(1, 2), Set.of(2, 8), Set.of(3, 9), Set.of(4, 10), Set.of(6, 7),
                Set.of(7, 8), Set.of(9, 15), Set.of(10, 16), Set.of(12, 13), Set.of(13, 19),
                Set.of(14, 15), Set.of(14, 20), Set.of(16, 22), Set.of(16, 17), Set.of(18, 19),
                Set.of(19, 25), Set.of(20, 26), Set.of(21, 27), Set.of(22, 28), Set.of(25, 31),
                Set.of(26, 32), Set.of(27, 33), Set.of(28, 29), Set.of(31, 32), Set.of(32, 33),
                Set.of(34, 35)
        ));
    }

    private HashSet<Set<Integer>> loadFifthLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(0, 6), Set.of(1, 7), Set.of(2, 8), Set.of(3, 9), Set.of(7, 13),
                Set.of(8, 14), Set.of(10, 11), Set.of(10, 16), Set.of(11, 17), Set.of(13, 14),
                Set.of(14, 20), Set.of(15, 21), Set.of(15, 16), Set.of(18, 19), Set.of(19, 25),
                Set.of(20, 26), Set.of(21, 22), Set.of(22, 23), Set.of(22, 28), Set.of(24, 25),
                Set.of(26, 32), Set.of(27, 33), Set.of(27, 28), Set.of(30, 31)));
    }

    private HashSet<Set<Integer>> loadSixthLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(0, 1), Set.of(2, 3), Set.of(3, 9), Set.of(6, 7), Set.of(7, 8),
                Set.of(8, 9), Set.of(10, 16), Set.of(10, 11), Set.of(13, 19), Set.of(13, 14),
                Set.of(14, 20), Set.of(14, 15), Set.of(15, 16), Set.of(18, 24), Set.of(19, 20),
                Set.of(21, 22), Set.of(22, 23), Set.of(25, 31), Set.of(25, 26), Set.of(26, 32),
                Set.of(26, 27), Set.of(27, 28), Set.of(28, 34), Set.of(33, 34)));
    }

    private HashSet<Set<Integer>> loadSeventhLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(1, 7), Set.of(2, 8), Set.of(3, 4), Set.of(6, 7), Set.of(8, 9),
                Set.of(8, 14), Set.of(9, 15), Set.of(10, 16), Set.of(10, 11), Set.of(12, 18),
                Set.of(13, 19), Set.of(13, 14), Set.of(15, 21), Set.of(15, 16), Set.of(17, 23),
                Set.of(19, 20), Set.of(21, 27), Set.of(22, 28), Set.of(22, 23), Set.of(24, 25),
                Set.of(25, 31), Set.of(25, 26), Set.of(26, 32), Set.of(27, 33), Set.of(28, 29)));
    }

    private HashSet<Set<Integer>> loadEighthLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(0, 1), Set.of(2, 8), Set.of(3, 4), Set.of(7, 13), Set.of(8, 14),
                Set.of(8, 9), Set.of(9, 15), Set.of(10, 16), Set.of(10, 11), Set.of(12, 13),
                Set.of(14, 20), Set.of(15, 21), Set.of(16, 17), Set.of(18, 19), Set.of(19, 25),
                Set.of(20, 21), Set.of(21, 27), Set.of(22, 28), Set.of(23, 29), Set.of(24, 25),
                Set.of(25, 26), Set.of(26, 32), Set.of(27, 33), Set.of(28, 34), Set.of(29, 35)));
    }

    private HashSet<Set<Integer>> loadNinthLabyrinth() {
        return new HashSet<>(Set.of(
                Set.of(0, 1), Set.of(2, 8), Set.of(3, 9), Set.of(6, 7), Set.of(7, 8),
                Set.of(9, 15), Set.of(9, 10), Set.of(10, 11), Set.of(13, 19), Set.of(14, 20),
                Set.of(14, 15), Set.of(16, 22), Set.of(16, 17), Set.of(18, 19), Set.of(19, 20),
                Set.of(21, 27), Set.of(21, 22), Set.of(22, 28), Set.of(24, 25), Set.of(25, 26),
                Set.of(26, 27), Set.of(28, 29), Set.of(29, 35), Set.of(31, 32), Set.of(33, 34)));
    }

    private ArrayList<LabyrinthVertex> setDefaultVertexes() {
        var vertexes = new ArrayList<LabyrinthVertex>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                vertexes.add(new LabyrinthVertex(i * 6 + j));
            }
        }
        return vertexes;
    }

    private boolean getPath(LabyrinthVertex start, LabyrinthVertex end, HashSet<LabyrinthVertex> passed, LinkedList<LabyrinthVertex> path) {
        passed.add(start);
        if (start.equals(end)) {
            path.addFirst(start);
            return true;
        }
        for (var vertex : start.getNeighbors()) {
            if (!passed.contains(vertex)) {
                if (getPath(vertex, end, passed, path)) {
                    path.addFirst(start);
                    return true;
                }
            }
        }
        return false;
    }

    public LinkedList<LabyrinthVertex> getPath() {
        var passed = new HashSet<LabyrinthVertex>();
        var path = new LinkedList<LabyrinthVertex>();
        getPath(getTable().stream().filter(n -> n.getCoordinate() == getStart()).findAny().get(),
                getTable().stream().filter(n -> n.getCoordinate() == getDestination()).findAny().get(),
                passed, path);
        return path;
    }

    private int getNumericValue(char a, char b) {return (Character.getNumericValue(a) - 1) * 6 + Character.getNumericValue(b) - 1;}
}
