package ru.mas.ktane_bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.CreateBombService;
import ru.mas.ktane_bot.handlers.CreateMessageService;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.MessageDto;
import ru.mas.ktane_bot.model.MessageType;
import ru.mas.ktane_bot.model.modules.*;

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
        if (!update.hasMessage() || (!update.getMessage().hasText() && !update.getMessage().hasSticker())) {
            return;
        }

        String message = "";
        if (update.getMessage().hasText())
            message = update.getMessage().getText();
        else if (update.getMessage().hasSticker())
            message = update.getMessage().getSticker().getFileId();

        var userId = String.valueOf(update.getMessage().getChatId());

        if (!dataCache.hasUser(userId))
            dataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);

        var state = dataCache.getUsersCurrentBotState(userId);

        if (message.startsWith(KNOBS)) {
            sendMessage(solverMap.get("knobsSolver").solve(message, userId));
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
            case CREATE_BOMB:
                sendMessage(createBombService.createBomb(message, userId));
                break;
            case DEFAULT:
                switch (message) {
                    case NEWBOMB:
                        dataCache.setUsersCurrentBotState(userId, BotState.CREATE_BOMB);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.BEGINCREATE);
                        sendMessage(createBombService.createBomb(message, userId));
                        break;
                    case WIRES:
                        dataCache.setUsersCurrentBotState(userId, BotState.WIRES);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите последовательность цветов проводов (Пример - gbbr (green blue blue red)) (b- blue, d - black)").build());
                        break;
                    case BUTTON:
                        dataCache.setUsersCurrentBotState(userId, BotState.BUTTON);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите кнопку (Пример: blue hold)").build());
                        break;
                    case KEYBOARD:
                        dataCache.setUsersCurrentBotState(userId, BotState.KEYBOARD);
                        dataCache.saveUserModule(userId, new Keyboard());
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Отправьте 4 стикера из стикерпака ниже, совпадающих с клавишами на вашей клавиатуре").build());
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("https://t.me/addstickers/Ktane_Keyboard").build());
                        break;
                    case MEMORY:
                        dataCache.setUsersCurrentBotState(userId, BotState.MEMORY);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.MEMORY1);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Вводите на каждом этапе цифры, сначала на экране, потом остальные (пример: 43214)").build());
                        break;
                    case LABYRINTH:
                        dataCache.setUsersCurrentBotState(userId, BotState.LABYRINTH);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите координаты кругов, затем вашего местонахождения, " +
                                        "затем местонахождение финиша в формате рядстолбец рядстолбец(пример: 21 36 44 66) ").build());
                        break;
                    case SIMON_SAYS:
                        dataCache.setUsersCurrentBotState(userId, BotState.SIMON_SAYS);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Вводите в каждом этапе цвет (пример: g - зеленый, y- желтый)," +
                                        " если этапы кончились напишите stop").build());
                        break;
                    case MORSE:
                        dataCache.setUsersCurrentBotState(userId, BotState.MORSE);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите первую букву").build());
                        break;
                    case WHOSONFIRST:
                        dataCache.setUsersCurrentBotState(userId, BotState.WHOS_ON_FIRST);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.WHOSONFIRST1);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Вводите в каждом этапе через запятую все слова, в порядке сначала то, " +
                                        "которое на экране, а потом клавиатуру слева направо сверху вниз").build());
                        break;
                    case PASSWORD:
                        dataCache.setUsersCurrentBotState(userId, BotState.PASSWORD);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.PASSWORD1);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Вводите сначала все первые буквы, затем все вторые, пока не получите ответ (Пример: abcdef)").build());
                        break;
                    case COMPWIRES:
                        dataCache.setUsersCurrentBotState(userId, BotState.COMP_WIRES);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Вводите по одному проводу " +
                                        "(пример: rbsl, где r - красный, b - синий, s - звезда, l - светодиод, _ - просто белый провод)").build());
                        break;
                    case WIRESEQ:
                        dataCache.setUsersCurrentBotState(userId, BotState.WIRE_SEQ);
                        dataCache.saveUserModule(userId, new WireSeq());
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Вводите по 0-3 проводам в формате darbbc, где\n" +
                                "d - черный, b - синий, r - красный, a - A, b - Б, c - В, если проводов нет, то _").build());
                        break;
                    case EMOJI_MATH:
                        dataCache.setUsersCurrentBotState(userId, BotState.EMOJI_MATH);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите выражение:").build());
                        break;
                    case CRAZY_TALK:
                        dataCache.setUsersCurrentBotState(userId, BotState.CRAZY_TALK);
                        dataCache.saveUserModule(userId, new CrazyTalk());
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите первое слово").build());
                        break;
                    case LETTER_KEYS:
                        dataCache.setUsersCurrentBotState(userId, BotState.LETTER_KEYS);
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите ваше число").build());
                        break;
                    case BULB:
                        dataCache.setUsersCurrentBotState(userId, BotState.BULB);
                        dataCache.saveUserModule(userId, new Bulb());
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите включена ли лампа (on/off)," +
                                        " полупрозрачная она или сплошная (see-through/opaque) и какого она цвета через пробел").build());
                        break;
                    case PIANO_KEYS:
                        dataCache.setUsersCurrentBotState(userId, BotState.PIANO_KEYS);
                        dataCache.saveUserModule(userId, new PianoKeys());
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Отправьте 3 стикера из стикерпака ниже, совпадающих с клавишами на экране").build());
                        sendMessage(MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("https://t.me/addstickers/Ktane_PianoKeys").build());
                        break;
                }
                break;
            default:
                sendMessage(solverMap.get(state.getSolverBeanName()).solve(message, userId));
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

    private void sendMessage(MessageDto messageDto) {
        try {
            switch (messageDto.getMessageType()) {
                case TEXT: {
                    execute(CreateMessageService.createTextMessage(messageDto));
                    break;
                }
                case STICKER: {
                    execute(CreateMessageService.createStickerMessage(messageDto));
                    break;
                }
                case STICKER_LIST: {
                    for (var sticker : CreateMessageService.createStickersMessageList(messageDto))
                        execute(sticker);
                    break;
                }
            }
        } catch (TelegramApiException e) {
            System.out.println("asd");
        }
    }
}
