package ru.mas.ktane_bot.bot.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotState {
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
    PIANO_KEYS("pianoKeysSolver"),
    CONNECTION_CHECK("connectionCheckSolver"),
    TWO_BITS("twoBitsSolver"),
    CODE("codeSolver"),
    LISTENING("listeningSolver"),
    SWITCHES("switchesSolver"),
    ASTROLOGY("astrologySolver"),
    SHAPE_SHIFT("shapeShiftSolver"),
    PRESS_X("pressXSolver"),
    COLOUR_FLASH("colourFlashSolver"),
    DEFAULT (null);

    private final String solverBeanName;
}
