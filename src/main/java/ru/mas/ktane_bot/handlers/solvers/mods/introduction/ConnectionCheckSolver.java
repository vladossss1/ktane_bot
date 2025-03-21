package ru.mas.ktane_bot.handlers.solvers.mods.introduction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.connectioncheck.ConnectionCheckGraphName;
import ru.mas.ktane_bot.model.modules.mods.introduction.ConnectionCheckModule;

import java.util.StringJoiner;
import java.util.stream.Collectors;

@Component("connectionCheckSolver")
@RequiredArgsConstructor
public class ConnectionCheckSolver implements Solver {

    private final DataCache dataCache;
    private static final String CONNECT = "Соединить";
    private static final String DONT_CONNECT = "Не соединять";
    @Override
    public MessageDto solve(String message, String userId) {
        var bomb = dataCache.getUserBomb(userId);
        var module = (ConnectionCheckModule) dataCache.getUserModule(userId);
        int serialNumberPosition;
        if (message.chars().mapToObj(c -> (char)c).collect(Collectors.toSet()).size() == message.length())
            serialNumberPosition = bomb.getSerialNumber().length() - 1;
        else if (message.matches("\\S*1\\S*1\\S*"))
            serialNumberPosition = 0;
        else if (message.matches("\\S*7\\S*7\\S*"))
            serialNumberPosition = bomb.getSerialNumber().length() - 1;
        else if (message.matches("\\S*2\\S*2\\S*2\\S*"))
            serialNumberPosition = 1;
        else if (!message.contains("5"))
            serialNumberPosition = 4;
        else if (message.matches("[^8]*8[^8]*8[^8]*"))
            serialNumberPosition = 2;
        else if (bomb.getBatteriesCount() > 6 || bomb.getBatteriesCount() == 0)
            serialNumberPosition = bomb.getSerialNumber().length() - 1;
        else
            serialNumberPosition = bomb.getBatteriesCount() - 1;

        module.loadGraph(ConnectionCheckGraphName.getBySymbol(bomb.getSerialNumber().charAt(serialNumberPosition)));

        dataCache.solveModule(userId);
        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text(getResult(module, message)).build();
    }

    public String getResult(ConnectionCheckModule module, String message) {
        var result = new StringJoiner("\n");
        for (int i = 0; i < message.length(); i += 2) {
            result.add(module.isConnected(Character.getNumericValue(message.charAt(i)),
                    Character.getNumericValue(message.charAt(i+1))) ? CONNECT : DONT_CONNECT);
        }
        return result.toString();
    }
}
