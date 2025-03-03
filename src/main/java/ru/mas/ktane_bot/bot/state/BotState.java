package ru.mas.ktane_bot.bot.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotState {
    DEFAULT (null),
    CREATEBOMB (null),
    WIRES ("wiresSolver"),
    BUTTON ("buttonsSolver"),
    KEYBOARD ("keyboardSolver"),
    MEMORY ("memorySolver"),
    LABYRINTH ("labyrinthSolver"),
    SIMONSAYS ("simonSaysSolver"),
    MORSE ("morseSolver"),
    WHOSONFIRST ("whosOnFirstSolver"),
    PASSWORD ("passwordSolver"),
    COMPWIRES ("compWiresSolver"),
    WIRESEQ ("wireSeqSolver"),
    EMOJIMATH ("emojiMathSolver"),
    CRAZYTALK ("crazyTalkSolver"),
    LETTERKEYS("letterKeysSolver"),
    BULB("bulbSolver");

    private final String solverBeanName;
}
