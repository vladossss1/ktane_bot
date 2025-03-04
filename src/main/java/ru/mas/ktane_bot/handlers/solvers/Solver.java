package ru.mas.ktane_bot.handlers.solvers;

import ru.mas.ktane_bot.model.MessageDto;

public interface Solver {
    MessageDto solve(String message, String userId);
}
