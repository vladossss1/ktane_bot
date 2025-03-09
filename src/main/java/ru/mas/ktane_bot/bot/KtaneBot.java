package ru.mas.ktane_bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mas.ktane_bot.handlers.UpdateMessageHandler;
import ru.mas.ktane_bot.service.CreateMessageService;
import ru.mas.ktane_bot.model.message.MessageDto;

@Component
public class KtaneBot extends TelegramLongPollingBot {
    private final CreateMessageService createMessageService;
    private final UpdateMessageHandler updateMessageHandler;

    public KtaneBot(@Value("${bot.token}") String botToken, CreateMessageService createMessageService,
                    UpdateMessageHandler updateMessageHandler) {
        super(botToken);
        this.createMessageService = createMessageService;
        this.updateMessageHandler = updateMessageHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        MessageDto preparedMessage = updateMessageHandler.handle(update);
        sendMessage(preparedMessage);
    }

    @Override
    public String getBotUsername() {
        return "Ktane_Bot";
    }

    private void sendMessage(MessageDto messageDto) {
        try {
            switch (messageDto.getMessageType()) {
                case TEXT: {
                    execute(createMessageService.createTextMessage(messageDto));
                    break;
                }
                case STICKER: {
                    execute(createMessageService.createStickerMessage(messageDto));
                    break;
                }
                case STICKER_LIST: {
                    for (var sticker : createMessageService.createStickersMessageList(messageDto))
                        execute(sticker);
                    break;
                }
                case TEXT_LIST: {
                    for (var text : createMessageService.creatTextMessageList(messageDto))
                        execute(text);
                    break;
                }
            }
        } catch (TelegramApiException e) {
            System.out.println("asd");
        }
    }
}
