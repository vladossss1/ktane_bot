package ru.mas.ktane_bot.handlers;

import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.cache.UserDataCache;

public abstract class Handler {

    public final UserDataCache userDataCache;

    public Handler(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    public abstract String handle(String message, Long userId);
}
