package ru.mas.ktane_bot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class LabyrinthVertex {
    private int coordinate;
    private List<LabyrinthVertex> neighbors;

    public LabyrinthVertex(int coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabyrinthVertex that)) return false;
        return Objects.equals(getCoordinate(), that.getCoordinate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinate());
    }
}
