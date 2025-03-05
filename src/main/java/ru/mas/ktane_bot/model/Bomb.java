package ru.mas.ktane_bot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    private int batteriesHoldersCount;

    private int portHoldersCount;

    private int emptyPortHoldersCount;

    private int modulesCount;

    private int solvedModulesCount = 0;

    private int errorsCount = 0;

    public void addIndicator(Indicator indicator) {
        indicators.add(indicator);
    }

    public void addPort(PortType port) {
        ports.add(port);
    }

    public boolean isLastDigitOfSerialNumber(boolean odd) {
        return serialNumber.chars().mapToObj(ch -> (char) ch).filter(Character::isDigit).reduce((a, b) -> b)
                .filter(character -> character % 2 == (odd ? 1 : 0)).isPresent();
    }

    public void solveModule() {
        solvedModulesCount++;
    }

    public void addMistake() {
        errorsCount++;
    }

    public boolean isDone() {
        return modulesCount == solvedModulesCount;
    }

    public boolean serialHasVowel() {
        return serialHasSymbol("[aeiou]");
    }

    public boolean hasIndicator(String name, boolean lit) {
        return indicators.stream().anyMatch(i -> i.getName().equals(name) && i.getLit().equals(lit));
    }

    public boolean hasIndicator(String name) {
        return indicators.stream().anyMatch(i -> i.getName().equals(name));
    }

    public boolean hasOneOfIndicators(String names) {
        return indicators.stream().anyMatch(i -> i.getName().matches(names));
    }

    public boolean serialHasSymbol(String pattern) {
        return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(serialNumber).find();
    }

    public boolean hasPort(PortType port) {
        return ports.contains(port);
    }
}
