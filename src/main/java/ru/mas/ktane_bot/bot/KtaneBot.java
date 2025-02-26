package ru.mas.ktane_bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.UserDataCache;
import ru.mas.ktane_bot.handlers.CreateBombHandler;
import ru.mas.ktane_bot.handlers.Handler;
import ru.mas.ktane_bot.handlers.solvers.*;

import static ru.mas.ktane_bot.props.Command.*;

@Component
public class KtaneBot extends TelegramLongPollingBot {
    private final UserDataCache userDataCache;
    private Handler handler;

    public KtaneBot(@Value("${bot.token}") String botToken, UserDataCache userDataCache) {
        super(botToken);
        this.userDataCache = userDataCache;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var message = update.getMessage().getText();
        var userId = update.getMessage().getChatId();

        if(!userDataCache.hasUser(userId))
            userDataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);

        var state = userDataCache.getUsersCurrentBotState(userId);

        if (message.equals(MISTAKE) && userDataCache.hasBomb(userId)) {
            var bomb = userDataCache.getUserBomb(userId);
            bomb.addMistake();
            userDataCache.saveUserBomb(userId, bomb);
            return;
        }

        if (message.equals(SOLVE) && userDataCache.hasBomb(userId)) {
            var bomb = userDataCache.getUserBomb(userId);
            bomb.solveModule();
            userDataCache.saveUserBomb(userId, bomb);
            return;
        }

        switch (state) {
            case CREATEBOMB:
                handler = new CreateBombHandler(userDataCache);
                sendMessage(userId, handler.handle(message, userId));
                break;
            case WIRES:
                handler = new WiresSolver(userDataCache);
                sendMessage(userId, handler.handle(message, userId));
                break;
            case BUTTON:
                handler = new ButtonsSolver(userDataCache);
                sendMessage(userId, handler.handle(message, userId));
                break;
            case KEYBOARD:
                handler = new KeyboardSolver(userDataCache);
                sendMessage(userId, handler.handle(message, userId));
                break;
            case MEMORY:
                handler = new MemorySolver(userDataCache);
                sendMessage(userId, handler.handle(message, userId));
                break;
            case LABYRINTH:
                handler = new LabyrinthSolver(userDataCache);
                sendMessage(userId, handler.handle(message, userId));
                break;
            case DEFAULT:
                switch (message) {
                    case NEWBOMB:
                        userDataCache.setUsersCurrentBotState(userId, BotState.CREATEBOMB);
                        userDataCache.setUsersCurrentBotSubState(userId, BotSubState.BEGINCREATE);
                        handler = new CreateBombHandler(userDataCache);
                        sendMessage(userId, handler.handle(message, userId));
                        break;
                    case WIRES:
                        userDataCache.setUsersCurrentBotState(userId, BotState.WIRES);
                        sendMessage(userId, "Введите последовательность цветов проводов (Пример - gbbr (green blue blue red)) (b- blue, d - black)");
                        break;
                    case BUTTON:
                        userDataCache.setUsersCurrentBotState(userId, BotState.BUTTON);
                        sendMessage(userId,"Введите кнопку (Пример: blue hold)");
                        break;
                    case KEYBOARD:
                        userDataCache.setUsersCurrentBotState(userId, BotState.KEYBOARD);
                        sendMessage(userId, "Введите символы через пробел (Пример: эё ае з зтв)");
                        break;
                    case MEMORY:
                        userDataCache.setUsersCurrentBotState(userId, BotState.MEMORY);
                        userDataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY1);
                        sendMessage(userId, "Вводите на каждом этапе цифры, сначала на экране, потом остальные (пример: 4 3 2 1 4)");
                        break;
                    case LABYRINTH:
                        userDataCache.setUsersCurrentBotState(userId, BotState.LABYRINTH);
                        sendMessage(userId, "Введите координаты кругов, затем вашего местонахождения, " +
                                "затем местонахождение финиша в формате ряд столбец,ряд столбец(пример: 2 1,3 6,4 4,6 6) ");
                }
        }
        if (userDataCache.getUsersCurrentBotState(userId).equals(BotState.DEFAULT) && userDataCache.hasBomb(userId) && userDataCache.getUserBomb(userId).isDone()) {
            userDataCache.saveUserBomb(userId, null);
            sendMessage(userId, "Бомба решена, ура!");
        }
    }

    @Override
    public String getBotUsername() {
        return "Ktane_Bot";
    }

    private void sendMessage(Long chatId, String text) {
        var sendMessage = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            System.out.println("asd");
        }
    }
}
