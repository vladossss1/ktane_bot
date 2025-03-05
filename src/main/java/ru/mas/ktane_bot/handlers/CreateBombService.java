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
        switch (subState) {
            case BEGINCREATE:
                dataCache.saveUserBomb(userId, new Bomb());
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.SERIALNUMBER);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите серийный номер:").build();
            case SERIALNUMBER:
                bomb.setSerialNumber(message.toUpperCase());
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.LIT_INDICATORS);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите горящие индикаторы через пробел или _ если их нет:").build();
            case LIT_INDICATORS:
                if (!message.equals("_"))
                    add_indicators(bomb, message.toUpperCase(), true);
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.UNLIT_INDICATORS);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите негорящие индикаторы через пробел или _ если их нет:").build();
            case UNLIT_INDICATORS:
                if (!message.equals("_"))
                    add_indicators(bomb, message.toUpperCase(), false);
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.D_BATTERIES);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите количество батареек типа D:").build();
            case D_BATTERIES:
                bomb.setDBatteriesCount((Integer.parseInt(message)));
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.AA_BATTERIES);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите количество батареек типа AA:").build();
            case AA_BATTERIES:
                bomb.setAaBatteriesCount((Integer.parseInt(message)));
                bomb.setBatteriesCount(bomb.getDBatteriesCount() + bomb.getAaBatteriesCount());
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.BATTERIES_SLOTS);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите количество слотов под батарейки:").build();
            case BATTERIES_SLOTS:
                bomb.setBatteriesHoldersCount((Integer.parseInt(message)));
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.PORTS);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                        .text("Введите порты через пробел (если портов несколько нужно ввести несколько раз) или _ если их нет:").build();
            case PORTS:
                if (!message.equals("_"))
                    add_ports(bomb, message.toUpperCase());
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.PORT_HOLDERS_SLOTS);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите количество держателей для портов:").build();
            case PORT_HOLDERS_SLOTS:
                bomb.setPortHoldersCount((Integer.parseInt(message)));
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, BotSubState.MODULES);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите количество модулей:").build();
            case MODULES:
                bomb.setModulesCount((Integer.parseInt(message)));
                dataCache.saveUserBomb(userId, bomb);
                dataCache.setUsersCurrentBotSubState(userId, null);
                dataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);
                return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Ваша бомба: " + bomb).build();
        }
        return null;
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
