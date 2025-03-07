package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.mods.introduction.CrazyTalkModule;

import java.util.Map;

@Component("crazyTalkSolver")
@RequiredArgsConstructor
public class CrazyTalkSolver implements Solver {

    private final DataCache dataCache;

    private final static Map<String, String> wordMap = Map.<String, String>ofEntries(
            Map.entry("<- <- -> <- -> ->", "5/4"),
            Map.entry("1 3 2 4", "3/2"),
            Map.entry("LEFT ARROW LEFT WORD RIGHT ARROW LEFT WORD RIGHT ARROW RIGHT WORD", "5/8"),
            Map.entry("BLANK", "1/3"),
            Map.entry("LITERALLY BLANK", "1/5"),
            Map.entry("FOR THE LOVE OF ALL THAT IS GOOD AND HOLY PLEASE FULLSTOP FULLSTOP.", "9/0"),
            Map.entry("AN ACTUAL LEFT ARROW LITERAL PHRASE", "5/3"),
            Map.entry("FOR THE LOVE OF - THE DISPLAY JUST CHANGED, I DIDN’T KNOW THIS MOD COULD DO THAT. DOES IT MENTION THAT IN THE MANUAL?", "8/7"),
            Map.entry("ALL WORDS ONE THREE TO FOR FOR AS IN THIS IS FOR YOU", "4/0"),
            Map.entry("LITERALLY NOTHING", "1/4"),
            Map.entry("NO, LITERALLY NOTHING", "2/5"),
            Map.entry("THE WORD LEFT", "7/0"),
            Map.entry("HOLD ON IT’S BLANK", "1/9"),
            Map.entry("SEVEN WORDS FIVE WORDS THREE WORDS THE PUNCTUATION FULLSTOP", "0/5"),
            Map.entry("THE PHRASE THE WORD STOP TWICE", "9/1"),
            Map.entry("THE FOLLOWING SENTENCE THE WORD NOTHING", "2/7"),
            Map.entry("ONE THREE TO FOR", "3/9"),
            Map.entry("THREE WORDS THE WORD STOP", "7/3"),
            Map.entry("DISREGARD WHAT I JUST SAID. FOUR WORDS, NO PUNCTUATION. ONE THREE 2 4.", "3/1"),
            Map.entry("1 3 2 FOR", "1/0"),
            Map.entry("DISREGARD WHAT I JUST SAID. TWO WORDS THEN TWO DIGITS. ONE THREE 2 4.", "0/8"),
            Map.entry("WE JUST BLEW UP", "4/2"),
            Map.entry("NO REALLY.", "5/2"),
            Map.entry("<- LEFT -> LEFT -> RIGHT", "5/6"),
            Map.entry("ONE AND THEN 3 TO 4", "4/7"),
            Map.entry("STOP TWICE", "7/6"),
            Map.entry("LEFT", "6/9"),
            Map.entry("..", "8/5"),
            Map.entry("PERIOD PERIOD", "8/2"),
            Map.entry("THERE ARE THREE WORDS NO PUNCTUATION READY? STOP DOT PERIOD", "5/0"),
            Map.entry("NOVEBMER OSCAR SPACE, LIMA INDIGO TANGO ECHO ROMEO ALPHA LIMA LIMA YANKEE SPACE NOVEMBER OSCAR TANGO HOTEL INDEGO NOVEMBER GOLF", "2/9"),
            Map.entry("FIVE WORDS THREE WORDS THE PUNCTUATION FULLSTOP", "1/9"),
            Map.entry("THE PHRASE: THE PUNCTUATION FULLSTOP", "9/3"),
            Map.entry("EMPTY SPACE", "1/6"),
            Map.entry("ONE THREE TWO FOUR", "3/7"),
            Map.entry("IT’S SHOWING NOTHING", "2/3"),
            Map.entry("LIMA ECHO FOXTROT TANGO SPACE ALPHA ROMEO ROMEO OSCAR RISKY SPACE SIERRA YANKEE MIKE BRAVO OSCAR LIMA", "1/2"),
            Map.entry("ONE 2 3 4", "3/4"),
            Map.entry("STOP.", "7/4"),
            Map.entry(".PERIOD", "8/1"),
            Map.entry("NO REALLY STOP", "5/1"),
            Map.entry("1 3 TOO 4", "8/0"),
            Map.entry("PERIOD TWICE", "8/3"),
            Map.entry("1 3 TOO WITH 2 OHS FOUR", "4/2"),
            Map.entry("1 3 TO 4", "3/0"),
            Map.entry("STOP DOT PERIOD", "5/0"),
            Map.entry("LEFT LEFT RIGHT LEFT RIGHT RIGHT", "6/7"),
            Map.entry("IT LITERALLY SAYS THE WORD ONE AND THEN THE NUMBERS 2 3 4", "3/5"),
            Map.entry("WAIT FORGET EVERYTHING I JUST SAID, TWO WORDS THEN TWO SYMBOLS THEN TWO WORDS: - - RIGHT LEFT - -", "1/6"),
            Map.entry("1 THREE TWO FOUR", "3/6"),
            Map.entry("PERIOD", "7/9"),
            Map.entry(".STOP", "7/8"),
            Map.entry("NOVEBMER OSCAR SPACE, LIMA INDIA TANGO ECHO ROMEO ALPHA LIMA LIMA YANKEE SPACE NOVEMBER OSCAR TANGO HOTEL INDIA NOVEMBER GOLF", "0/7"),
            Map.entry("LIMA ECHO FOXTROT TANGO SPACE ALPHA ROMEO ROMEO OSCAR WHISKEY SPACE SIERRA YANKEE MIKE BRAVO OSCAR LIMA", "6/5"),
            Map.entry("NOTHING", "1/2"),
            Map.entry("THERE’S NOTHING", "1/8"),
            Map.entry("STOP STOP", "7/5"),
            Map.entry("RIGHT ALL IN WORDS STARTING NOW ONE TWO THREE FOUR", "4/9"),
            Map.entry("THE PHRASE THE WORD LEFT", "7/1"),
            Map.entry("LEFT ARROW SYMBOL TWICE THEN THE WORDS RIGHT LEFT RIGHT THEN A RIGHT ARROW SYMBOL", "5/9"),
            Map.entry("LEFT LEFT RIGHT - RIGHT -", "5/7"),
            Map.entry("NO COMMA LITERALLY NOTHING", "2/4"),
            Map.entry("HOLD ON CRAZY TALK WHILE I DO THIS NEEDY", "2/1"),
            Map.entry("THIS ONE IS ALL ARROW SYMBOLS NO WORDS", "2/8"),
            Map.entry("<-", "6/3"),
            Map.entry("THE WORD STOP TWICE", "9/4"),
            Map.entry("<- <- RIGHT LEFT -> ->", "6/1"),
            Map.entry("THE PUNCTUATION FULLSTOP", "9/2"),
            Map.entry("1 3 TOO WITH TWO OS 4", "4/1"),
            Map.entry("THREE WORDS THE PUNCTUATION FULLSTOP", "9/9"),
            Map.entry("OK WORD FOR WORD LEFT ARROW SYMBOL TWICE THEN THE WORDS RIGHT LEFT RIGHT THEN A RIGHT ARROW SYMBOL", "6/0"),
            Map.entry("DOT DOT", "8/6"),
            Map.entry("LEFT ARROW", "6/8"),
            Map.entry("AFTER I SAY BEEP FIND THIS PHRASE WORD FOR WORD BEEP AN ACTUAL LEFT ARROW", "7/2"),
            Map.entry("ONE THREE 2 WITH TWO OHS 4", "4/3"),
            Map.entry("LEFT ARROW SYMBOL", "6/4"),
            Map.entry("AN ACTUAL LEFT ARROW", "6/2"),
            Map.entry("THAT’S WHAT IT’S SHOWING", "2/1"),
            Map.entry("THE PHRASE THE WORD NOTHING", "2/6"),
            Map.entry("THE WORD ONE AND THEN THE NUMBERS 3 2 4", "4/8"),
            Map.entry("ONE 3 2 FOUR", "3/8"),
            Map.entry("ONE WORD THEN PUNCTUATION. STOP STOP.", "0/9"),
            Map.entry("THE WORD BLANK", "0/1"),
            Map.entry("FULLSTOP FULLSTOP", "8/4")
    );

    @Override
    public MessageDto solve(String message, String userId) {
        var module = (CrazyTalkModule) dataCache.getUserModule(userId);
        String result;
        if (message.equals("_")) {
            dataCache.solveModule(userId);
            result = wordMap.get(module.getCurrentWords().get(module.getCurrentWords().indexOf(module.getCurrentResult())));
        } else {
            if (module.getCurrentWords().isEmpty()) {
                module.setCurrentWords(wordMap.keySet().stream().filter(w -> w.startsWith(message.toUpperCase())).toList());
                module.setCurrentResult(message.toUpperCase());
            } else {
                module.setCurrentResult(module.getCurrentResult() + " " + message.toUpperCase());
                module.setCurrentWords(module.getCurrentWords().stream().filter(w -> w.startsWith(module.getCurrentResult())).toList());
            }
            if (module.getCurrentWords().size() == 1) {
                dataCache.solveModule(userId);
                result = wordMap.get(module.getCurrentWords().get(0));
            } else {
                dataCache.saveUserModule(userId, module);
                return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
            }
        }
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }
}
