package ru.mas.ktane_bot.model.modules.shapeshift;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ShapeType {
    CIRCLE("Круг"),
    TRIANGLE("Треугольник"),
    TICKET("Билет"),
    RECTANGLE("Прямоугольник");

    private final String answer;

    ShapeType(String answer) {
        this.answer = answer;
    }

    public static ShapeType getByName(String name) {
        return Arrays.stream(ShapeType.values()).filter(s -> s.name().equalsIgnoreCase(name)).findFirst().get();
    }
}
