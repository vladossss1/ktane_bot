package ru.mas.ktane_bot.cache;

import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.model.Bomb;
import ru.mas.ktane_bot.model.modules.BombModule;

public interface DataCache {
    void setUsersCurrentBotState(String userId, BotState botState);

    BotState getUsersCurrentBotState(String userId);

    Bomb getUserBomb(String userId);

    void saveUserBomb(String userId, Bomb bomb);

    boolean hasUser(String userId);

    void setUsersCurrentBotSubState(String userId, BotSubState botSubState);

    BotSubState getUsersCurrentBotSubState(String userId);

    BombModule getUserModule(String userId);

    void saveUserModule(String userId, BombModule module);

    boolean hasBomb(String userId);

    void solveModule(String userId);
}
