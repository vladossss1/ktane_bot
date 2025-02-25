package ru.mas.ktane_bot.cache;

import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.model.Bomb;

public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);

    Bomb getUserBomb(long userId);

    void saveUserBomb(long userId, Bomb bomb);

    boolean hasUser(long userId);

    void setUsersCurrentBotSubState(long userId, BotSubState botSubState);

    BotSubState getUsersCurrentBotSubState(long userId);
}
