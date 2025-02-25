package ru.mas.ktane_bot.handlers;

import ru.mas.ktane_bot.bot.state.BotState;

public interface Handler {
    String handle(String message, Long userId);
}
