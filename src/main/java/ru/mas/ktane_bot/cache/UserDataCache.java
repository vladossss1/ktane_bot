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
    private final Map<String, BotState> usersBotStates = new HashMap<>();
    private final Map<String, BotSubState> usersBotSubStates = new HashMap<>();
    private final Map<String, Bomb> usersBombs = new HashMap<>();
    private final Map<String, BombModule> usersModules = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(String userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(String userId) {
        var botState = usersBotStates.get(userId);
        if (botState == null)
            botState = BotState.DEFAULT;

        return botState;
    }

    @Override
    public Bomb getUserBomb(String userId) {
        return usersBombs.get(userId);
    }

    @Override
    public void saveUserBomb(String userId, Bomb bomb) {
        usersBombs.put(userId, bomb);
    }

    @Override
    public boolean hasUser(String userId) {
        return usersBotStates.containsKey(userId);
    }

    @Override
    public void setUsersCurrentBotSubState(String userId, BotSubState botSubState) {
        usersBotSubStates.put(userId, botSubState);
    }

    @Override
    public BotSubState getUsersCurrentBotSubState(String userId) {
        return usersBotSubStates.get(userId);
    }

    @Override
    public BombModule getUserModule(String userId) {
        return usersModules.get(userId);
    }

    @Override
    public void saveUserModule(String userId, BombModule module) {
        usersModules.put(userId, module);
    }

    @Override
    public boolean hasBomb(String userId) {
        return usersBombs.containsKey(userId);
    }

    @Override
    public void solveModule(String userId) {
        setUsersCurrentBotState(userId, BotState.DEFAULT);
        setUsersCurrentBotSubState(userId, null);
        saveUserModule(userId, null);
        getUserBomb(userId).solveModule();
    }
}
