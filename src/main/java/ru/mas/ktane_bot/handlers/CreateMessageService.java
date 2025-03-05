package ru.mas.ktane_bot.handlers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.stickers.StickerSet;
import ru.mas.ktane_bot.model.MessageDto;

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
}
