package ru.mas.ktane_bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.CreateBombService;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.modules.CrazyTalk;
import ru.mas.ktane_bot.model.modules.WireSeq;

import java.util.Map;

import static ru.mas.ktane_bot.props.Command.*;

@Component
public class KtaneBot extends TelegramLongPollingBot {
    private final DataCache dataCache;
    private final CreateBombService createBombService;
    private final Map<String, Solver> solverMap;

    public KtaneBot(@Value("${bot.token}") String botToken, DataCache dataCache,
                    CreateBombService createBombService, Map<String, Solver> solverMap) {
        super(botToken);
        this.dataCache = dataCache;
        this.createBombService = createBombService;
        this.solverMap = solverMap;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var message = update.getMessage().getText();
        var userId = update.getMessage().getChatId();

        if(!dataCache.hasUser(userId))
            dataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);

        var state = dataCache.getUsersCurrentBotState(userId);

        if (message.startsWith(KNOBS)) {
            sendMessage(userId, solverMap.get("knobsSolver").solve(message, userId));
            return;
        }

        if (message.equals(CANCEL_MODULE)) {
            dataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);
            dataCache.saveUserModule(userId, null);
        }

        if (message.equals(CANCEL_BOMB)) {
            dataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);
            dataCache.saveUserModule(userId, null);
            dataCache.saveUserBomb(userId, null);
        }

        if (message.equals(MISTAKE) && dataCache.hasBomb(userId)) {
            var bomb = dataCache.getUserBomb(userId);
            bomb.addMistake();
            dataCache.saveUserBomb(userId, bomb);
            return;
        }

        if (message.equals(SOLVE) && dataCache.hasBomb(userId)) {
            var bomb = dataCache.getUserBomb(userId);
            bomb.solveModule();
            dataCache.saveUserBomb(userId, bomb);
            return;
        }

        switch (state) {
            case CREATEBOMB:
                sendMessage(userId, createBombService.createBomb(message, userId));
                break;
            case DEFAULT:
                switch (message) {
                    case NEWBOMB:
                        dataCache.setUsersCurrentBotState(userId, BotState.CREATEBOMB);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.BEGINCREATE);
                        sendMessage(userId, createBombService.createBomb(message, userId));
                        break;
                    case WIRES:
                        dataCache.setUsersCurrentBotState(userId, BotState.WIRES);
                        sendMessage(userId, "Введите последовательность цветов проводов (Пример - gbbr (green blue blue red)) (b- blue, d - black)");
                        break;
                    case BUTTON:
                        dataCache.setUsersCurrentBotState(userId, BotState.BUTTON);
                        sendMessage(userId,"Введите кнопку (Пример: blue hold)");
                        break;
                    case KEYBOARD:
                        dataCache.setUsersCurrentBotState(userId, BotState.KEYBOARD);
                        sendMessage(userId, "Введите символы через пробел (Пример: эё ае з зтв)");
                        break;
                    case MEMORY:
                        dataCache.setUsersCurrentBotState(userId, BotState.MEMORY);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY1);
                        sendMessage(userId, "Вводите на каждом этапе цифры, сначала на экране, потом остальные (пример: 43214)");
                        break;
                    case LABYRINTH:
                        dataCache.setUsersCurrentBotState(userId, BotState.LABYRINTH);
                        sendMessage(userId, "Введите координаты кругов, затем вашего местонахождения, " +
                                "затем местонахождение финиша в формате рядстолбец рядстолбец(пример: 21 36 44 66) ");
                        break;
                    case SIMON_SAYS:
                        dataCache.setUsersCurrentBotState(userId, BotState.SIMONSAYS);
                        sendMessage(userId, "Вводите в каждом этапе цвет (пример: g - зеленый, y- желтый)," +
                                " если этапы кончились напишите stop");
                        break;
                    case MORSE:
                        dataCache.setUsersCurrentBotState(userId, BotState.MORSE);
                        sendMessage(userId, "Введите первую букву");
                        break;
                    case WHOSONFIRST:
                        dataCache.setUsersCurrentBotState(userId, BotState.WHOSONFIRST);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.WHOSONFIRST1);
                        sendMessage(userId, "Вводите в каждом этапе через запятую все слова, в порядке сначала то, " +
                                "которое на экране, а потом клавиатуру слева направо сверху вниз");
                        break;
                    case PASSWORD:
                        dataCache.setUsersCurrentBotState(userId, BotState.PASSWORD);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.PASSWORD1);
                        sendMessage(userId, "Вводите сначала все первые буквы, затем все вторые, пока не получите ответ (Пример: abcdef)");
                        break;
                    case COMPWIRES:
                        dataCache.setUsersCurrentBotState(userId, BotState.COMPWIRES);
                        sendMessage(userId, "Вводите по одному проводу " +
                                "(пример: rbsl, где r - красный, b - синий, s - звезда, l - светодиод, _ - просто белый провод)");
                        break;
                    case WIRESEQ:
                        dataCache.setUsersCurrentBotState(userId, BotState.WIRESEQ);
                        dataCache.saveUserModule(userId, new WireSeq());
                        sendMessage(userId, "Вводите по 0-3 проводам в формате darbbc, где\n" +
                                "d - черный, b - синий, r - красный, a - A, b - Б, c - В, если проводов нет, то _");
                        break;
                    case EMOJI_MATH:
                        dataCache.setUsersCurrentBotState(userId, BotState.EMOJIMATH);
                        sendMessage(userId, "Введите выражение:");
                        break;
                    case CRAZY_TALK:
                        dataCache.setUsersCurrentBotState(userId, BotState.CRAZYTALK);
                        dataCache.saveUserModule(userId, new CrazyTalk());
                        sendMessage(userId, "Введите первое слово");
                        break;
                }
                break;
            default:
                sendMessage(userId, solverMap.get(state.getSolverBeanName()).solve(message, userId));
        }
//        if (userDataCache.getUsersCurrentBotState(userId).equals(BotState.DEFAULT) && userDataCache.hasBomb(userId) && userDataCache.getUserBomb(userId).isDone()) {
//            userDataCache.saveUserBomb(userId, null);
//            sendMessage(userId, "Бомба решена, ура!");
//        }
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
}
