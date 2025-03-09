package ru.mas.ktane_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import ru.mas.ktane_bot.model.message.MessageDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateMessageService {

    public SendMessage createTextMessage(MessageDto messageDto) {
        return new SendMessage(messageDto.getUserId(), messageDto.getText());
    }

    public SendSticker createStickerMessage(MessageDto messageDto) {
        return new SendSticker(messageDto.getUserId(), messageDto.getSticker());
    }

    public List<SendSticker> createStickersMessageList(MessageDto messageDto) {
        var stickers = new ArrayList<SendSticker>();
        messageDto.getStickers().forEach(s -> stickers.add(new SendSticker(messageDto.getUserId(), s)));
        return stickers;
    }

    public List<SendMessage> creatTextMessageList(MessageDto messageDto) {
        var messages = new ArrayList<SendMessage>();
        messageDto.getTexts().forEach(t -> messages.add(new SendMessage(messageDto.getUserId(), t)));
        return messages;
    }
}
