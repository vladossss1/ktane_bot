package ru.mas.ktane_bot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mas.ktane_bot.bot.state.BotState;
import ru.mas.ktane_bot.bot.state.BotSubState;
import ru.mas.ktane_bot.cache.DataCache;
import ru.mas.ktane_bot.handlers.solvers.Solver;
import ru.mas.ktane_bot.model.message.MessageDto;
import ru.mas.ktane_bot.model.message.MessageType;
import ru.mas.ktane_bot.model.modules.listening.ListeningButtons;
import ru.mas.ktane_bot.model.modules.mods.introduction.*;
import ru.mas.ktane_bot.model.modules.vanilla.*;
import ru.mas.ktane_bot.service.CreateBombService;

import java.util.List;
import java.util.Map;

import static ru.mas.ktane_bot.props.Command.*;
import static ru.mas.ktane_bot.props.Command.BULB;
import static ru.mas.ktane_bot.props.Command.PIANO_KEYS;

@Component
@RequiredArgsConstructor
public class UpdateMessageHandler {

    private final DataCache dataCache;
    private final CreateBombService createBombService;
    private final Map<String, Solver> solverMap;

    public MessageDto handle(Update update) {

        var message = "";
        var userId = "";
        if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getData();
            userId = String.valueOf(update.getCallbackQuery().getFrom().getId());
            dataCache.getUserModule(userId).setMessageWithKeyboardId(update.getCallbackQuery().getMessage().getMessageId());
        } else {
            userId = String.valueOf(update.getMessage().getChatId());
            if (update.getMessage().hasText())
                message = update.getMessage().getText();
            else if (update.getMessage().hasSticker())
                message = update.getMessage().getSticker().getFileId();
            else
                return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }

        if (!dataCache.hasState(userId))
            dataCache.setUsersCurrentBotState(userId, BotState.DEFAULT);

        var state = dataCache.getUsersCurrentBotState(userId);

        if (message.startsWith(KNOBS)) {
            return solverMap.get("knobsSolver").solve(message, userId);
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
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }

        if (message.equals(SOLVE) && dataCache.hasBomb(userId)) {
            var bomb = dataCache.getUserBomb(userId);
            bomb.solveModule();
            dataCache.saveUserBomb(userId, bomb);
            return MessageDto.builder().messageType(MessageType.NO_MESSAGE).build();
        }

        switch (state) {
            case CREATE_BOMB:
                return createBombService.createBomb(message, userId);
            case DEFAULT:
                switch (message) {
                    case NEWBOMB:
                        dataCache.setUsersCurrentBotState(userId, BotState.CREATE_BOMB);
                        dataCache.setUsersCurrentBotSubState(userId, BotSubState.BEGINCREATE);
                        return createBombService.createBomb(message, userId);
                    case WIRES:
                        dataCache.setUsersCurrentBotState(userId, BotState.WIRES);
                        dataCache.saveUserModule(userId, new WiresModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите последовательность цветов проводов (Пример - gbbr (green blue blue red)) (b- blue, d - black)").build();
                    case BUTTON:
                        dataCache.setUsersCurrentBotState(userId, BotState.BUTTON);
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите кнопку (Пример: blue hold)").build();
                    case KEYBOARD:
                        dataCache.setUsersCurrentBotState(userId, BotState.KEYBOARD);
                        dataCache.saveUserModule(userId, new KeyboardModule());
                        return MessageDto.builder()
                                .messageType(MessageType.TEXT_LIST).userId(userId).texts(List.of(
                                        "Отправьте 4 стикера из стикерпака ниже, совпадающих с клавишами на вашей клавиатуре",
                                        "https://t.me/addstickers/Ktane_Keyboard"
                                )).build();
                    case MEMORY:
                        dataCache.setUsersCurrentBotState(userId, BotState.MEMORY);
                        dataCache.saveUserModule(userId, new MemoryModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Вводите на каждом этапе цифры, сначала на экране, потом остальные (пример: 43214)").build();
                    case LABYRINTH:
                        dataCache.setUsersCurrentBotState(userId, BotState.LABYRINTH);
                        dataCache.saveUserModule(userId, new LabyrinthModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите координаты кругов, затем вашего местонахождения, " +
                                        "затем местонахождение финиша в формате рядстолбец рядстолбец(пример: 21 36 44 66) ").build();
                    case SIMON_SAYS:
                        dataCache.setUsersCurrentBotState(userId, BotState.SIMON_SAYS);
                        dataCache.saveUserModule(userId, new SimonSaysModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Вводите в каждом этапе цвет (пример: g - зеленый, y- желтый)," +
                                        " если этапы кончились напишите stop").build();
                    case MORSE:
                        dataCache.setUsersCurrentBotState(userId, BotState.MORSE);
                        dataCache.saveUserModule(userId, new MorseModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Вводите буквы, пока не получите ответ").build();
                    case WHOSONFIRST:
                        dataCache.setUsersCurrentBotState(userId, BotState.WHOS_ON_FIRST);
                        dataCache.saveUserModule(userId, new WhosOnFirstModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Вводите в каждом этапе через запятую все слова, в порядке сначала то, " +
                                        "которое на экране, а потом клавиатуру слева направо сверху вниз").build();
                    case PASSWORD:
                        dataCache.setUsersCurrentBotState(userId, BotState.PASSWORD);
                        dataCache.saveUserModule(userId, new PasswordModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Вводите сначала все первые буквы, затем все вторые, пока не получите ответ (Пример: abcdef)").build();
                    case COMPWIRES:
                        dataCache.setUsersCurrentBotState(userId, BotState.COMP_WIRES);
                        dataCache.saveUserModule(userId, new CompWiresModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите все провода через пробел " +
                                        "(пример: rbsl, где r - красный, b - синий, s - звезда, l - светодиод, _ - просто белый провод)").build();
                    case WIRESEQ:
                        dataCache.setUsersCurrentBotState(userId, BotState.WIRE_SEQ);
                        dataCache.saveUserModule(userId, new WireSeqModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Вводите по 0-3 проводам в формате darbbc, где\n" +
                                "d - черный, b - синий, r - красный, a - A, b - Б, c - В, если проводов нет, то _").build();
                    case EMOJI_MATH:
                        dataCache.setUsersCurrentBotState(userId, BotState.EMOJI_MATH);
                        dataCache.saveUserModule(userId, new EmojiMathModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите выражение:").build();
                    case CRAZY_TALK:
                        dataCache.setUsersCurrentBotState(userId, BotState.CRAZY_TALK);
                        dataCache.saveUserModule(userId, new CrazyTalkModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите первое слово").build();
                    case LETTER_KEYS:
                        dataCache.setUsersCurrentBotState(userId, BotState.LETTER_KEYS);
                        dataCache.saveUserModule(userId, new LetterKeysModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId).text("Введите ваше число").build();
                    case BULB:
                        dataCache.setUsersCurrentBotState(userId, BotState.BULB);
                        dataCache.saveUserModule(userId, new BulbModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите включена ли лампа (on/off)," +
                                        " полупрозрачная она или сплошная (see-through/opaque) и какого она цвета через пробел").build();
                    case PIANO_KEYS:
                        dataCache.setUsersCurrentBotState(userId, BotState.PIANO_KEYS);
                        dataCache.saveUserModule(userId, new PianoKeysModule());
                        return MessageDto.builder()
                                .messageType(MessageType.TEXT_LIST).userId(userId).texts(List.of(
                                        "Отправьте 3 стикера из стикерпака ниже, совпадающих с клавишами на экране",
                                        "https://t.me/addstickers/Ktane_PianoKeys"
                                )).build();
                    case CONNECTION_CHECK:
                        dataCache.setUsersCurrentBotState(userId, BotState.CONNECTION_CHECK);
                        dataCache.saveUserModule(userId, new ConnectionCheckModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите все восемь цифр:").build();
                    case TWO_BITS:
                        dataCache.setUsersCurrentBotState(userId, BotState.TWO_BITS);
                        state = dataCache.getUsersCurrentBotState(userId);
                        dataCache.saveUserModule(userId, new TwoBitsModule());
                        return MessageDto.builder().messageType(MessageType.TEXT_LIST).userId(userId)
                                .texts(List.of("Введите две буквы на бомбе, после этого присылайте по две цифры и следуйте указаниям"
                                        , solverMap.get(state.getSolverBeanName()).solve(message, userId).getText())).build();
                    case CODE:
                        dataCache.setUsersCurrentBotState(userId, BotState.CODE);
                        dataCache.saveUserModule(userId, new CodeModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите число с экрана").build();
                    case LISTENING:
                        dataCache.setUsersCurrentBotState(userId, BotState.LISTENING);
                        dataCache.saveUserModule(userId, new ListeningModule());
                        return MessageDto.builder().messageType(MessageType.TEXT_WITH_KEYBOARD).userId(userId)
                                .text("Нажмите на клавиатуре на звук, который услышали")
                                .inlineKeyboard(ListeningButtons.getInlineKeyboard()).build();
                    case SWITCHES:
                        dataCache.setUsersCurrentBotState(userId, BotState.SWITCHES);
                        dataCache.saveUserModule(userId, new SwitchesModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите положения переключателей и положения, в которые нужно прийти в формате:" +
                                        " \"uuuuu ddddd\", где u - поднят, d- опущен").build();
                    case ASTROLOGY:
                        dataCache.setUsersCurrentBotState(userId, BotState.ASTROLOGY);
                        dataCache.saveUserModule(userId, new AstrologyModule());
                        return MessageDto.builder()
                                .messageType(MessageType.TEXT_LIST).userId(userId).texts(List.of(
                                        "Отправьте 3 стикера из стикерпака ниже, совпадающих с картинками на экране",
                                        "https://t.me/addstickers/Ktane_Astrology"
                                )).build();
                    case SHAPE_SHIFT:
                        dataCache.setUsersCurrentBotState(userId, BotState.SHAPE_SHIFT);
                        dataCache.saveUserModule(userId, new ShapeShiftModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Введите через пробел левую и правую часть фигуры. (Пример: \"ticket circle\"," +
                                        " варианты: ticket, circle, triangle, rectangle)").build();
                    case PRESS_X:
                        dataCache.setUsersCurrentBotState(userId, BotState.PRESS_X);
                        state = dataCache.getUsersCurrentBotState(userId);
                        dataCache.saveUserModule(userId, new PressXModule());
                        return solverMap.get(state.getSolverBeanName()).solve(message, userId);
                    case COLOUR_FLASH:
                        dataCache.setUsersCurrentBotState(userId, BotState.COLOUR_FLASH);
                        dataCache.saveUserModule(userId, new ColourFlashModule());
                        return MessageDto.builder().messageType(MessageType.TEXT).userId(userId)
                                .text("Cначала введите все цвета слов, а потом сами слова через пробел. Пример: rygbbmwm mbwrygbm" +
                                        "(Возможные цвета: r - красный, y - желтый, g - зеленый, b - синий, m - розовый, w - белый").build();
                }
                break;
            default:
                return solverMap.get(state.getSolverBeanName()).solve(message, userId);
        }
        return null;
    }
}
