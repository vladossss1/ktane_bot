package ru.mas.ktane_bot.model.modules.mods.introduction;

import lombok.Getter;
import ru.mas.ktane_bot.model.bomb.Bomb;
import ru.mas.ktane_bot.model.bomb.BombAttribute;
import ru.mas.ktane_bot.model.modules.BombModule;
import ru.mas.ktane_bot.model.modules.shapeshift.Shape;
import ru.mas.ktane_bot.model.modules.shapeshift.ShapeType;

import java.util.ArrayList;
import java.util.List;

import static ru.mas.ktane_bot.model.bomb.PortType.*;

@Getter
public class ShapeShiftModule extends BombModule {
    List<Shape> shapes = new ArrayList<>();

    public ShapeShiftModule() {
        super(List.of(BombAttribute.SERIALNUMBER, BombAttribute.BATTERIES, BombAttribute.PORTS, BombAttribute.INDICATORS));
    }

    public void fillShapes(Bomb bomb) {
        for (var shapeType1 : ShapeType.values()) {
            for (var shapeType2 : ShapeType.values()) {
                shapes.add(new Shape (shapeType1, shapeType2));
            }
        }
        shapes.get(0).setNext(bomb.serialHasVowel() ? shapes.get(10) : shapes.get(12));
        shapes.get(1).setNext(bomb.hasIndicator("SIG", true) ? shapes.get(10) : shapes.get(15));
        shapes.get(2).setNext(bomb.getAaBatteriesCount() > 1 ? shapes.get(11) : shapes.get(14));
        shapes.get(3).setNext(bomb.hasIndicator("SND", true) ? shapes.get(0) : shapes.get(2));
        shapes.get(4).setNext(bomb.hasPort(PARALLEL) ? shapes.get(13) : shapes.get(3));
        shapes.get(5).setNext(bomb.hasIndicator("IND", true) ? shapes.get(6) : shapes.get(15));
        shapes.get(6).setNext(bomb.hasPort(RJ_45) ? shapes.get(1) : shapes.get(0));
        shapes.get(7).setNext(bomb.hasIndicator("CAR", false) ? shapes.get(8) : shapes.get(2));
        shapes.get(8).setNext(bomb.hasPort(STEREO_RCA) ? shapes.get(13) : shapes.get(3));
        shapes.get(9).setNext(bomb.hasPort(PS_2) ? shapes.get(6) : shapes.get(4));
        shapes.get(10).setNext(bomb.getBatteriesCount() > 2 ? shapes.get(11) : shapes.get(14));
        shapes.get(11).setNext(bomb.hasIndicator("FRQ", false) ? shapes.get(12) : shapes.get(5));
        shapes.get(12).setNext(bomb.hasPort(DVI_D) ? shapes.get(1) : shapes.get(9));
        shapes.get(13).setNext(bomb.hasIndicator("MSA", true) ? shapes.get(10) : shapes.get(7));
        shapes.get(14).setNext(bomb.hasIndicator("BOB", false) ? shapes.get(8) : shapes.get(5));
        shapes.get(15).setNext(bomb.isLastDigitOfSerialNumberEven() ? shapes.get(4) : shapes.get(7));
    }
}
