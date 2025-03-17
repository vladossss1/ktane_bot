package ru.mas.ktane_bot.model.modules.shapeshift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Shape {
    ShapeType leftSide;
    ShapeType rightSide;
    Shape next;
    boolean been;

    public Shape(ShapeType leftSide, ShapeType rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape shape)) return false;
        return leftSide == shape.leftSide && rightSide == shape.rightSide;
    }
}
