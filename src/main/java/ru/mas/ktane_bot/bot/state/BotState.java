package ru.mas.ktane_bot.bot.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotState {
    DEFAULT (null),
    CREATE_BOMB (null),
    WIRES ("wiresSolver"),
    BUTTON ("buttonsSolver"),
    KEYBOARD ("keyboardSolver"),
    MEMORY ("memorySolver"),
    LABYRINTH ("labyrinthSolver"),
    SIMON_SAYS ("simonSaysSolver"),
    MORSE ("morseSolver"),
    WHOS_ON_FIRST ("whosOnFirstSolver"),
    PASSWORD ("passwordSolver"),
    COMP_WIRES ("compWiresSolver"),
    WIRE_SEQ ("wireSeqSolver"),
    EMOJI_MATH ("emojiMathSolver"),
    CRAZY_TALK ("crazyTalkSolver"),
    LETTER_KEYS("letterKeysSolver"),
    BULB("bulbSolver"),
    PIANO_KEYS("pianoKeysSolver");

    private final String solverBeanName;
}
