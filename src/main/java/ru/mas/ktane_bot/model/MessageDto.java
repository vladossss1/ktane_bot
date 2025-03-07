package ru.mas.ktane_bot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.List;

@Getter
@Setter
@Builder
public class MessageDto {
    String text;
    List<String> texts;
    String userId;
    InputFile sticker;
    List<InputFile> stickers;
    MessageType messageType;
}
