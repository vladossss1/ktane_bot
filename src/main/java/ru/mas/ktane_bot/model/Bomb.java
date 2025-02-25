package ru.mas.ktane_bot.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class Bomb {

    private String serialNumber;

    private List<Indicator> indicators = new ArrayList<>();

    private List<PortType> ports = new ArrayList<>();

    private int dBatteriesCount;

    private int aaBatteriesCount;

    private int batteriesCount;

    private int batteriesSlotsCount;

    private int portHoldersCount;

    private int modulesCount;

    private int solvedModulesCount;

    private int errorsCount;

    public void addIndicator(Indicator indicator) {
        indicators.add(indicator);
    }

    public void addPort(PortType port) {
        ports.add(port);
    }

    public boolean isLastDigit(boolean odd) {
        return serialNumber.chars().mapToObj(ch -> (char)ch).filter(Character::isDigit).reduce((a, b) -> b)
                .filter(character -> character % 2 == (odd ? 1 : 0)).isPresent();
    }

    public void solveModule() {
        solvedModulesCount++;
    }
}
