package ru.mas.ktane_bot.handlers;

import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.model.Bomb;
import ru.mas.ktane_bot.model.Indicator;
import ru.mas.ktane_bot.model.PortType;

import java.util.List;

@Component
public class CreateBombHandler extends Handler {

    public CreateBombHandler(UserDataCache userDataCache) {
        super(userDataCache);
    }

    @Override
    public String handle(String message, Long userId) {
        var subState = userDataCache.getUsersCurrentBotSubState(userId);
        var bomb = userDataCache.getUserBomb(userId);
        switch (subState) {
            case BEGINCREATE:
                userDataCache.saveUserBomb(userId, new Bomb());
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.SERIALNUMBER);
                return "Введите серийный номер:";
            case SERIALNUMBER:
                bomb.setSerialNumber(message);
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.LIT_INDICATORS);
                return "Введите горящие индикаторы через запятую или _ если их нет:";
            case LIT_INDICATORS:
                if (!message.equals("_"))
                    add_indicators(bomb, message, true);
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.UNLIT_INDICATORS);
                return "Введите негорящие индикаторы через запятую или _ если их нет:";
            case UNLIT_INDICATORS:
                if (!message.equals("_"))
                    add_indicators(bomb, message, false);
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.D_BATTERIES);
                return "Введите количество батареек типа D:";
            case D_BATTERIES:
                bomb.setDBatteriesCount((Integer.parseInt(message)));
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.AA_BATTERIES);
                return "Введите количество батареек типа AA:";
            case AA_BATTERIES:
                bomb.setAaBatteriesCount((Integer.parseInt(message)));
                bomb.setBatteriesCount(bomb.getDBatteriesCount() + bomb.getAaBatteriesCount());
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.BATTERIES_SLOTS);
                return "Введите количество слотов под батарейки:";
            case BATTERIES_SLOTS:
                bomb.setBatteriesSlotsCount((Integer.parseInt(message)));
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.PORTS);
                return "Введите порты через запятую (если портов несколько нужно ввести несколько раз) или _ если их нет:";
            case PORTS:
                if (!message.equals("_"))
                    add_ports(bomb, message);
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.PORT_HOLDERS_SLOTS);
                return "Введите количество держателей для портов:";
            case PORT_HOLDERS_SLOTS:
                bomb.setPortHoldersCount((Integer.parseInt(message)));
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, BotSubState.MODULES);
                return "Введите количество модулей:";
            case MODULES:
                bomb.setModulesCount((Integer.parseInt(message)));
                userDataCache.saveUserBomb(userId, bomb);
                userDataCache.setUsersCurrentBotSubState(userId, null);
                userDataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);
                return "Ваша бомба: " + bomb.toString();
        }
        return null;
    }

    private void add_indicators(Bomb bomb, String message, boolean lit) {
        var indicators = List.of(message.split(","));
        for (var indicator: indicators) {
            bomb.addIndicator(new Indicator(indicator, lit));
        }
    }

    private void add_ports(Bomb bomb, String message) {
        var ports = List.of(message.split(","));
        for (var port: ports) {
            bomb.addPort(PortType.get(port));
        }
    }
}
