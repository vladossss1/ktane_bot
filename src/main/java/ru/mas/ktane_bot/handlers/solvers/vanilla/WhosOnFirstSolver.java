package ru.mas.ktane_bot.handlers.solvers.vanilla;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component("whosOnFirstSolver")
@RequiredArgsConstructor
public class WhosOnFirstSolver implements Solver {

    private final DataCache dataCache;

    private static final Map<String, Integer> stepOne = Map.ofEntries(
            Map.entry("да", 3), Map.entry("первое", 2), Map.entry("экран", 6),
            Map.entry("готово", 2), Map.entry("другой", 6), Map.entry("ничего", 3),
            Map.entry("_", 5), Map.entry("пусто", 4), Map.entry("нет", 6),
            Map.entry("весело", 3), Map.entry("весила", 6), Map.entry("весило", 4),
            Map.entry("корится", 4), Map.entry("корица", 5), Map.entry("кориться", 5),
            Map.entry("стой", 6), Map.entry("глаз", 4), Map.entry("глас", 6),
            Map.entry("порог", 4), Map.entry("порок", 4), Map.entry("парок", 1),
            Map.entry("краб", 6), Map.entry("крап", 5), Map.entry("крабб", 4),
            Map.entry("пас", 3), Map.entry("пасс", 6), Map.entry("паз", 2),
            Map.entry("погоди", 6)
    );

    private static final Map<String, List<String>> stepTwo = Map.ofEntries(
            Map.entry("готово", List.of("да", "постой", "ещё раз", "весила", "весило", "стой", "весело", "пусто", "готово", "нет", "первое", "еще раз", "ничего", "погоди")),
            Map.entry("первое", List.of("весило", "постой", "да", "весила", "нет", "весело", "ничего", "еще раз", "погоди", "готово", "пусто", "ещё раз", "стой", "первое")),
            Map.entry("нет", List.of("пусто", "еще раз", "погоди", "первое", "ещё раз", "готово", "весело", "да", "ничего", "весило", "стой", "постой", "нет", "весила")),
            Map.entry("пусто", List.of("погоди", "весело", "постой", "весила", "пусто", "стой", "готово", "ничего", "нет", "ещё раз", "весило", "еще раз", "да", "первое")),
            Map.entry("ничего", List.of("еще раз", "весело", "постой", "весила", "да", "пусто", "нет", "стой", "весило", "ещё раз", "погоди", "первое", "ничего", "готово")),
            Map.entry("да", List.of("постой", "весело", "еще раз", "весила", "первое", "ещё раз", "стой", "готово", "ничего", "да", "весило", "пусто", "нет", "погоди")),
            Map.entry("ещё раз", List.of("еще раз", "ещё раз", "весило", "ничего", "готово", "пусто", "весила", "нет", "постой", "первое", "погоди", "да", "стой", "весело")),
            Map.entry("еще раз", List.of("готово", "ничего", "весило", "ещё раз", "постой", "да", "весело", "нет", "стой", "пусто", "еще раз", "весила", "погоди", "первое")),
            Map.entry("весило", List.of("весело", "весило", "первое", "нет", "весила", "да", "пусто", "ещё раз", "еще раз", "погоди", "стой", "готово", "постой", "ничего")),
            Map.entry("весело", List.of("да", "ничего", "готово", "стой", "нет", "погоди", "ещё раз", "весело", "весила", "весило", "еще раз", "пусто", "постой", "первое")),
            Map.entry("весила", List.of("пусто", "готово", "постой", "ещё раз", "ничего", "стой", "нет", "погоди", "весило", "весила", "весело", "первое", "еще раз", "да")),
            Map.entry("постой", List.of("весила", "нет", "первое", "да", "еще раз", "ничего", "погоди", "постой", "весило", "готово", "пусто", "стой", "ещё раз", "весело")),
            Map.entry("погоди", List.of("еще раз", "нет", "пусто", "постой", "да", "весило", "первое", "стой", "ещё раз", "погоди", "ничего", "готово", "весело", "весила")),
            Map.entry("стой", List.of("весело", "весила", "да", "готово", "стой", "постой", "ничего", "еще раз", "пусто", "весило", "первое", "ещё раз", "нет", "погоди")),
            Map.entry("во все", List.of("че?", "вовсе", "все", "всё", "дальше", "пас", "и так", "чё?", "паз", "во все", "пасс", "что?", "жми", "итак")),
            Map.entry("вовсе", List.of("все", "дальше", "что?", "пас", "паз", "жми", "пасс", "чё?", "во все", "итак", "всё", "че?", "и так", "вовсе")),
            Map.entry("все", List.of("пасс", "вовсе", "пас", "все", "дальше", "и так", "че?", "итак", "всё", "во все", "паз", "чё?", "что?", "жми")),
            Map.entry("всё", List.of("во все", "всё", "и так", "дальше", "пасс", "вовсе", "итак", "все", "паз", "пас", "че?", "жми", "что?", "чё?")),
            Map.entry("и так", List.of("жми", "итак", "и так", "пас", "паз", "че?", "все", "чё?", "всё", "что?", "дальше", "пасс", "вовсе", "во все")),
            Map.entry("итак", List.of("пас", "че?", "дальше", "паз", "всё", "и так", "пасс", "жми", "итак", "во все", "что?", "чё?", "вовсе", "все")),
            Map.entry("пас", List.of("пас", "все", "вовсе", "во все", "жми", "чё?", "пасс", "дальше", "че?", "что?", "всё", "и так", "итак", "паз")),
            Map.entry("пасс", List.of("и так", "итак", "вовсе", "всё", "дальше", "пасс", "жми", "во все", "пас", "что?", "все", "че?", "чё?", "паз")),
            Map.entry("паз", List.of("во все", "чё?", "всё", "все", "итак", "жми", "пасс", "что?", "вовсе", "пас", "и так", "дальше", "паз", "че?")),
            Map.entry("жми", List.of("че?", "пас", "дальше", "паз", "все", "и так", "всё", "чё?", "что?", "во все", "итак", "вовсе", "пасс", "жми")),
            Map.entry("дальше", List.of("паз", "пас", "пасс", "все", "чё?", "че?", "дальше", "что?", "жми", "вовсе", "и так", "всё", "итак", "во все")),
            Map.entry("чё?", List.of("вовсе", "итак", "жми", "пасс", "во все", "и так", "че?", "паз", "всё", "дальше", "чё?", "пас", "все", "что?")),
            Map.entry("че?", List.of("вовсе", "жми", "что?", "всё", "во все", "чё?", "пас", "и так", "че?", "итак", "паз", "дальше", "все", "пасс")),
            Map.entry("что?", List.of("всё", "дальше", "итак", "и так", "чё?", "жми", "пасс", "паз", "пас", "во все", "что?", "че?", "вовсе", "все"))
    );

    @Override
    public MessageDto solve(String message, String userId) {
        var splitted = Arrays.stream(message.split(",")).toList();
        var subState = dataCache.getUsersCurrentBotSubState(userId);
        switch (subState) {
            case WHOSONFIRST1:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.WHOSONFIRST2);
                break;
            case WHOSONFIRST2:
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.WHOSONFIRST3);
                break;
            case WHOSONFIRST3:
                dataCache.setUsersCurrentBotSubState(userId, null);
                dataCache.solveModule(userId);
                break;
        }
        for (var word: stepTwo.get(splitted.get(stepOne.get(splitted.get(0))))) {
            if (splitted.subList(1, 7).contains(word))
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(word).build();
        }
        return null;
    }
}
