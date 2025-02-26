package ru.mas.ktane_bot.cache;

import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.model.Bomb;
import ru.mas.ktane_bot.model.modules.BombModule;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache{
    private final Map<Long, BotState> usersBotStates = new HashMap<>();
    private final Map<Long, BotSubState> usersBotSubStates = new HashMap<>();
    private final Map<Long, Bomb> usersBombs = new HashMap<>();
    private final Map<Long, BombModule> usersModules = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        var botState = usersBotStates.get(userId);
        if (botState == null)
            botState = BotState.DEFAULT;

        return botState;
    }

    @Override
    public Bomb getUserBomb(long userId) {
        return usersBombs.get(userId);
    }

    @Override
    public void saveUserBomb(long userId, Bomb bomb) {
        usersBombs.put(userId, bomb);
    }

    @Override
    public boolean hasUser(long userId) {
        return usersBotStates.containsKey(userId);
    }

    @Override
    public void setUsersCurrentBotSubState(long userId, BotSubState botSubState) {
        usersBotSubStates.put(userId, botSubState);
    }

    @Override
    public BotSubState getUsersCurrentBotSubState(long userId) {
        return usersBotSubStates.get(userId);
    }

    @Override
    public BombModule getUserModule(long userId) {
        return usersModules.get(userId);
    }

    @Override
    public void saveUserModule(long userId, BombModule module) {
        usersModules.put(userId, module);
    }

    @Override
    public boolean hasBomb(long userId) {
        return usersBombs.containsKey(userId);
    }
}
