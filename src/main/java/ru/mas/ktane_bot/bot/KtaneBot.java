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
import ru.mas.ktane_bot.model.modules.WireSeq;

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

        if (message.startsWith(KNOBS)) {
            handleModule(new KnobsSolver(userDataCache), message, userId);
            return;
        }

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
                handleModule(new CreateBombHandler(userDataCache), message, userId);
                break;
            case WIRES:
                handleModule(new WiresSolver(userDataCache), message, userId);
                break;
            case BUTTON:
                handleModule(new ButtonsSolver(userDataCache), message, userId);
                break;
            case KEYBOARD:
                handleModule(new KeyboardSolver(userDataCache), message, userId);
                break;
            case MEMORY:
                handleModule(new MemorySolver(userDataCache), message, userId);
                break;
            case LABYRINTH:
                handleModule(new LabyrinthSolver(userDataCache), message, userId);
                break;
            case SIMONSAYS:
                handleModule(new SimonSaysSolver(userDataCache), message, userId);
                break;
            case MORSE:
                handleModule(new MorseSolver(userDataCache), message, userId);
                break;
            case WHOSONFIRST:
                handleModule(new WhosOnFirstSolver(userDataCache), message, userId);
                break;
            case PASSWORD:
                handleModule(new PasswordSolver(userDataCache), message, userId);
                break;
            case COMPWIRES:
                handleModule(new CompWiresSolver(userDataCache), message, userId);
                break;
            case WIRESEQ:
                handleModule(new WireSeqSolver(userDataCache), message, userId);
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
                        sendMessage(userId, "Вводите на каждом этапе цифры, сначала на экране, потом остальные (пример: 43214)");
                        break;
                    case LABYRINTH:
                        userDataCache.setUsersCurrentBotState(userId, BotState.LABYRINTH);
                        sendMessage(userId, "Введите координаты кругов, затем вашего местонахождения, " +
                                "затем местонахождение финиша в формате рядстолбец рядстолбец(пример: 21 36 44 66) ");
                        break;
                    case SIMON_SAYS:
                        userDataCache.setUsersCurrentBotState(userId, BotState.SIMONSAYS);
                        sendMessage(userId, "Вводите в каждом этапе цвет (пример: g - зеленый, y- желтый)," +
                                " если этапы кончились напишите стоп");
                        break;
                    case MORSE:
                        userDataCache.setUsersCurrentBotState(userId, BotState.MORSE);
                        sendMessage(userId, "Введите первую букву");
                        break;
                    case WHOSONFIRST:
                        userDataCache.setUsersCurrentBotState(userId, BotState.WHOSONFIRST);
                        userDataCache.setUsersCurrentBotSubState(userId, BotSubState.WHOSONFIRST1);
                        sendMessage(userId, "Вводите в каждом этапе через запятую все слова, в порядке сначала то, " +
                                "которое на экране, а потом клавиатуру слева направо сверху вниз");
                        break;
                    case PASSWORD:
                        userDataCache.setUsersCurrentBotState(userId, BotState.PASSWORD);
                        userDataCache.setUsersCurrentBotSubState(userId, BotSubState.PASSWORD1);
                        sendMessage(userId, "Вводите сначала все первые буквы, затем все вторые, пока не получите ответ (Пример: abcdef)");
                        break;
                    case COMPWIRES:
                        userDataCache.setUsersCurrentBotState(userId, BotState.COMPWIRES);
                        sendMessage(userId, "Вводите по одному проводу " +
                                "(пример: rbsl, где r - красный, b - синий, s - звезда, l - светодиод, _ - просто белый провод)");
                        break;
                    case WIRESEQ:
                        userDataCache.setUsersCurrentBotState(userId, BotState.WIRESEQ);
                        userDataCache.saveUserModule(userId, new WireSeq());
                        sendMessage(userId, "Вводите по 1-3 проводам в формате darbbc, где\n" +
                                "d - черный, b - синий, r - красный, a - A, b - Б, c - В");
                        break;
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

    private void sendMessage(Long userId, String text) {
        var sendMessage = new SendMessage(String.valueOf(userId), text);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            System.out.println("asd");
        }
    }

    private void handleModule(Handler handler, String message, Long userId){
        sendMessage(userId, handler.handle(message, userId));
    }
}
