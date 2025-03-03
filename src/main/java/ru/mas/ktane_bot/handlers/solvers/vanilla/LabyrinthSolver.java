package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.modules.Labyrinth;
import ru.mas.ktane_bot.model.modules.LabyrinthVertex;

import java.util.*;

@Component("labyrinthSolver")
@RequiredArgsConstructor
public class LabyrinthSolver implements Solver {

    private Labyrinth labyrinth = new Labyrinth();

    private static final List<List<Integer>> circles = List.of(
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

    private final DataCache dataCache;

    @Override
    public String solve(String message, Long userId) {
        var splitted = Arrays.stream(message.split(" ")).toList();

        for (int i = 0; i < circles.size(); i++) {
            if (circles.get(i).containsAll(List.of(Arrays.stream(splitted.get(0).split("")).map(Integer::parseInt).reduce((a, b) -> (a - 1) * 6 + b - 1).get(),
                    Arrays.stream(splitted.get(1).split("")).map(Integer::parseInt).reduce((a, b) -> (a - 1) * 6 + b - 1).get()))) {
                switch (i) {
                    case 0:
                        loadFirstLabyrinth();
                        break;
                    case 1:
                        loadSecondLabyrinth();
                        break;
                    case 2:
                        loadThirdLabyrinth();
                        break;
                    case 3:
                        loadFourthLabyrinth();
                        break;
                    case 4:
                        loadFifthLabyrinth();
                        break;
                    case 5:
                        loadSixthLabyrinth();
                        break;
                    case 6:
                        loadSeventhLabyrinth();
                        break;
                    case 7:
                        loadEighthLabyrinth();
                        break;
                    case 8:
                        loadNinthLabyrinth();
                        break;
                }
                break;
            }
        }
        labyrinth.setStart(Arrays.stream(splitted.get(2).split("")).map(Integer::parseInt).reduce((a, b) -> (a - 1) * 6 + b - 1).get());
        labyrinth.setDestination(Arrays.stream(splitted.get(3).split("")).map(Integer::parseInt).reduce((a, b) -> (a - 1) * 6 + b - 1).get());
        var passed = new HashSet<LabyrinthVertex>();
        var path = new LinkedList<LabyrinthVertex>();
        labyrinth.getPath(labyrinth.getTable().stream().filter(n -> n.getCoordinate() == labyrinth.getStart()).findAny().get(),
                labyrinth.getTable().stream().filter(n -> n.getCoordinate() == labyrinth.getDestination()).findAny().get(),
                passed, path);
        dataCache.solveModule(userId);
        return buildPath(path);
    }

    private String buildPath(LinkedList<LabyrinthVertex> path) {
        var sb = new StringBuilder();
        for (int i = 1; i < path.size(); i++) {
            if (path.get(i).getCoordinate() - path.get(i - 1).getCoordinate() == 6)
                sb.append("вниз\n");
            else if (path.get(i).getCoordinate() - path.get(i - 1).getCoordinate() == -6)
                sb.append("вверх\n");
            else if (path.get(i).getCoordinate() - path.get(i - 1).getCoordinate() == -1)
                sb.append("влево\n");
            else
                sb.append("вправо\n");
        }
        return sb.toString();
    }

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

    private void loadLabyrinth(HashSet<Set<Integer>> walls) {
        var vertexes = setDefaultVertexes();
        for (int i = 0; i < 36; i++) {
            var neighbors = getNeighbors(i, walls);

            vertexes.get(i).setNeighbors(neighbors.stream().map(n -> vertexes.stream().filter(v -> n.equals(v.getCoordinate())).findFirst().get()).toList());
        }
        labyrinth.setTable(vertexes);
    }

    private void loadFirstLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(1, 7), Set.of(2, 3), Set.of(4, 10), Set.of(5, 11), Set.of(6, 7),
                Set.of(8, 14), Set.of(8, 9), Set.of(9, 15), Set.of(10, 16), Set.of(12, 13),
                Set.of(13, 19), Set.of(14, 15), Set.of(16, 22), Set.of(18, 19), Set.of(19, 25),
                Set.of(20, 26), Set.of(21, 22), Set.of(21, 27), Set.of(22, 28),
                Set.of(25, 31), Set.of(26, 27), Set.of(28, 29), Set.of(28, 34), Set.of(31, 32), Set.of(33, 34))));
    }

    private void loadSecondLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(0, 6), Set.of(2, 3), Set.of(2, 8), Set.of(7, 8), Set.of(9, 10),
                Set.of(5, 11), Set.of(7, 13), Set.of(9, 15), Set.of(10, 16), Set.of(12, 13),
                Set.of(14, 15), Set.of(14, 20), Set.of(16, 22), Set.of(19, 20), Set.of(21, 22),
                Set.of(21, 27), Set.of(19, 25), Set.of(22, 23), Set.of(24, 25), Set.of(25, 26),
                Set.of(26, 27), Set.of(28, 29), Set.of(28, 34), Set.of(30, 31), Set.of(32, 33))));
    }

    private void loadThirdLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(1, 7), Set.of(2, 3), Set.of(3, 4), Set.of(6, 12), Set.of(6, 7),
                Set.of(7, 8), Set.of(8, 9), Set.of(9, 15), Set.of(10, 16), Set.of(10, 11),
                Set.of(13, 14), Set.of(14, 15), Set.of(16, 17), Set.of(18, 19), Set.of(19, 20),
                Set.of(20, 21), Set.of(21, 22), Set.of(22, 23), Set.of(24, 25), Set.of(26, 27),
                Set.of(27, 28), Set.of(28, 29), Set.of(33, 34))));
    }

    private void loadFourthLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(1, 2), Set.of(2, 8), Set.of(3, 9), Set.of(4, 10), Set.of(6, 7),
                Set.of(7, 8), Set.of(9, 15), Set.of(10, 16), Set.of(12, 13), Set.of(13, 19),
                Set.of(14, 15), Set.of(14, 20), Set.of(16, 22), Set.of(16, 17), Set.of(18, 19),
                Set.of(19, 25), Set.of(20, 26), Set.of(21, 27), Set.of(22, 28), Set.of(25, 31),
                Set.of(26, 32), Set.of(27, 33), Set.of(28, 29), Set.of(31, 32), Set.of(32, 33),
                Set.of(34, 35)
        )));
    }

    private void loadFifthLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(0, 6), Set.of(1, 7), Set.of(2, 8), Set.of(3, 9), Set.of(7, 13),
                Set.of(8, 14), Set.of(10, 11), Set.of(10, 16), Set.of(11, 17), Set.of(13, 14),
                Set.of(14, 20), Set.of(15, 21), Set.of(15, 16), Set.of(18, 19), Set.of(19, 25),
                Set.of(20, 26), Set.of(21, 22), Set.of(22, 23), Set.of(22, 28), Set.of(24, 25),
                Set.of(26, 32), Set.of(27, 33), Set.of(27, 28), Set.of(30, 31))));
    }

    private void loadSixthLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(0, 1), Set.of(2, 3), Set.of(3, 9), Set.of(6, 7), Set.of(7, 8),
                Set.of(8, 9), Set.of(10, 16), Set.of(10, 11), Set.of(13, 19), Set.of(13, 14),
                Set.of(14, 20), Set.of(14, 15), Set.of(15, 16), Set.of(18, 24), Set.of(19, 20),
                Set.of(21, 22), Set.of(22, 23), Set.of(25, 31), Set.of(25, 26), Set.of(26, 32),
                Set.of(26, 27), Set.of(27, 28), Set.of(28, 34), Set.of(33, 34))));
    }

    private void loadSeventhLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(1, 7), Set.of(2, 8), Set.of(3, 4), Set.of(6, 7), Set.of(8, 9),
                Set.of(8, 14), Set.of(9, 15), Set.of(10, 16), Set.of(10, 11), Set.of(12, 18),
                Set.of(13, 19), Set.of(13, 14), Set.of(15, 21), Set.of(15, 16), Set.of(17, 23),
                Set.of(19, 20), Set.of(21, 27), Set.of(22, 28), Set.of(22, 23), Set.of(24, 25),
                Set.of(25, 31), Set.of(25, 26), Set.of(26, 32), Set.of(27, 33), Set.of(28, 29))));
    }

    private void loadEighthLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(0, 1), Set.of(2, 8), Set.of(3, 4), Set.of(7, 13), Set.of(8, 14),
                Set.of(8, 9), Set.of(9, 15), Set.of(10, 16), Set.of(10, 11), Set.of(12, 13),
                Set.of(14, 20), Set.of(15, 21), Set.of(16, 17), Set.of(18, 19), Set.of(19, 25),
                Set.of(20, 21), Set.of(21, 27), Set.of(22, 28), Set.of(23, 29), Set.of(24, 25),
                Set.of(25, 26), Set.of(26, 32), Set.of(27, 33), Set.of(28, 34), Set.of(29, 35))));
    }

    private void loadNinthLabyrinth() {
        loadLabyrinth(new HashSet<>(Set.of(
                Set.of(0, 1), Set.of(2, 8), Set.of(3, 9), Set.of(6, 7), Set.of(7, 8),
                Set.of(9, 15), Set.of(9, 10), Set.of(10, 11), Set.of(13, 19), Set.of(14, 20),
                Set.of(14, 15), Set.of(16, 22), Set.of(16, 17), Set.of(18, 19), Set.of(19, 20),
                Set.of(21, 27), Set.of(21, 22), Set.of(22, 28), Set.of(24, 25), Set.of(25, 26),
                Set.of(26, 27), Set.of(28, 29), Set.of(29, 35), Set.of(31, 32), Set.of(33, 34))));
    }

    private ArrayList<LabyrinthVertex> setDefaultVertexes() {
        labyrinth = new Labyrinth();
        var vertexes = new ArrayList<LabyrinthVertex>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                vertexes.add(new LabyrinthVertex(i * 6 + j));
            }
        }
        return vertexes;
    }
}
