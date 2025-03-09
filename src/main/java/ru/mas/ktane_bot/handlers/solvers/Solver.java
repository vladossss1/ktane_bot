package ru.mas.ktane_bot.handlers.solvers;

import ru.mas.ktane_bot.model.message.MessageDto;

public interface Solver {
    MessageDto solve(String message, String userId);
}
