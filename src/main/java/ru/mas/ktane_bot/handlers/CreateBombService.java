package ru.mas.ktane_bot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.model.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateBombService {

    private final DataCache dataCache;

    public MessageDto createBomb(String message, String userId) {
        var subState = dataCache.getUsersCurrentBotSubState(userId);
        var bomb = dataCache.getUserBomb(userId);
        var result = switch (subState) {
            case BEGINCREATE:
                dataCache.saveUserBomb(userId, new Bomb());
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.SERIALNUMBER);
                yield "Введите серийный номер:";
            case SERIALNUMBER:
                bomb.setSerialNumber(message.toUpperCase());
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.LIT_INDICATORS);
                yield "Введите горящие индикаторы через пробел или _ если их нет:";
            case LIT_INDICATORS:
                if (!message.equals("_"))
                    add_indicators(bomb, message.toUpperCase(), true);
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.UNLIT_INDICATORS);
                yield "Введите негорящие индикаторы через пробел или _ если их нет:";
            case UNLIT_INDICATORS:
                if (!message.equals("_"))
                    add_indicators(bomb, message.toUpperCase(), false);
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.D_BATTERIES);
                yield "Введите количество батареек типа D:";
            case D_BATTERIES:
                bomb.setDBatteriesCount((Integer.parseInt(message)));
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.AA_BATTERIES);
                yield "Введите количество батареек типа AA:";
            case AA_BATTERIES:
                bomb.setAaBatteriesCount((Integer.parseInt(message)));
                bomb.setBatteriesCount(bomb.getDBatteriesCount() + bomb.getAaBatteriesCount());
                bomb.setBatteriesHoldersCount(bomb.getAaBatteriesCount() / 2 + bomb.getDBatteriesCount());
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.PORTS);
                yield "Введите порты через пробел (если портов несколько нужно ввести несколько раз) или _ если их нет:";
            case PORTS:
                if (!message.equals("_"))
                    add_ports(bomb, message.toUpperCase());
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.PORT_HOLDERS_SLOTS);
                yield "Введите количество держателей для портов:";
            case PORT_HOLDERS_SLOTS:
                bomb.setPortHoldersCount((Integer.parseInt(message)));
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MODULES);
                yield "Введите количество модулей:";
            case MODULES:
                bomb.setModulesCount((Integer.parseInt(message)));
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, null);
                dataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);
                yield "Ваша бомба: " + bomb;
        };
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(result).build();
    }

    private void add_indicators(Bomb bomb, String message, boolean lit) {
        var indicators = List.of(message.split(" "));
        for (var indicator: indicators) {
            bomb.addIndicator(new Indicator(indicator, lit));
        }
    }

    private void add_ports(Bomb bomb, String message) {
        var ports = List.of(message.split(" "));
        for (var port: ports) {
            bomb.addPort(PortType.get(port));
        }
    }
}
