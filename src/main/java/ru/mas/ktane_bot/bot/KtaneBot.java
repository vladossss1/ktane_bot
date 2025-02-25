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
import ru.mas.ktane_bot.handlers.solvers.WiresSolver;

import static ru.mas.ktane_bot.props.Command.NEWBOMB;
import static ru.mas.ktane_bot.props.Command.WIRES;

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

        switch (state) {
            case CREATEBOMB:
                handler = new CreateBombHandler(userDataCache);
                sendMessage(userId, handler.handle(message, userId));
                break;
            case WIRES:
                handler = new WiresSolver(userDataCache);
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
                }

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
