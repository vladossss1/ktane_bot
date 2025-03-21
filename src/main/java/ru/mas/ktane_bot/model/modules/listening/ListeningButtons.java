package ru.mas.ktane_bot.model.modules.listening;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum ListeningButtons {
    TAXI("Диспетчерская такси", "&&&**"),
    COW("Корова", "&$#$&"),
    EXHAUST("Вытяжка", "$#$*&"),
    STATION("Вокзал", "#$$**"),
    ARCADE_MACHINE("Аркадный автомат", "$#$#*"),
    CASINO("Казино", "**$*#"),
    SUPERMARKET("Супермаркет", "#$$&*"),
    FOOTBALL_MATCH("Футбольный матч", "##*$*"),
    GRAY_HAZE("Серая неясыть", "$#*$&"),
    SEWING_MACHINE("Швейная машинка", "#&&*#"),
    COMMON_NIGHTINGALE("Обыкновенный соловей", "**#**"),
    CAR_ENGINE("Двигатель автомобиля", "&#**&"),
    RELOADING_GLOCK_19("Перезарядка Glock 19", "$&**#"),
    OBOE("Гобой", "&#$$#"),
    SAXOPHONE("Саксофон", "$&&**"),
    TUBA("Туба", "#&$##"),
    MARIMBA("Маримба", "&*$*$"),
    PHONE_RING("Звонок телефона", "&$$&*"),
    TIBETAN_MONKS("Тибетские монахи", "#&&&&"),
    THROAT_SINGING("Горловое пение", "**$$$"),
    BEACH("Пляж", "*&*&&"),
    MODEM_INTERNET("Модемный интернет", "*/**&"),
    POLICE_RADIO("Полицейская рация", "**###"),
    BEEPING("Запикивание", "&&$&*"),
    MEDIEVAL_SWORDS("Звон средневековых мечей", "&$**&"),
    CLOSING_DOOR("Закрывающаяся дверь", "#$#&$"),
    CHAINSAW("Бензопила", "&#&&#"),
    COMPRESSED_AIR("Сжатый воздух", "$$*$*"),
    SERVO_MOTOR("Серводвигатель", "$&#$$"),
    WATERFALL("Водопад", "&**$$"),
    TEARING_FABRIC("Рвущаяся ткань", "$&&*&"),
    ZIPPER("Застёжка-молния", "&$&##"),
    VACUUM_CLEANER("Пылесос", "#&$*&"),
    BALLPOINT_PEN("Письмо шариковой ручкой", "$*$**"),
    RATTLING_CHAIN("Гремучая цепь", "*#$&&"),
    PAGE_TURNING("Перелистывание страницы", "###&$"),
    TABLE_TENNIS("Настольный теннис", "*$$&$"),
    SQUEAKY_TOY("Пищащая игрушка", "$*&##"),
    HELICOPTER("Вертолёт", "#&$&&"),
    FIREWORK_EXPLOSION("Взрыв салюта", "$&$$*"),
    GLASS_BREAKING("Бой стекла", "*$*$*");

    private final String text;
    private final String answer;

    ListeningButtons(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public static InlineKeyboardMarkup getInlineKeyboard() {
        var keyboardRowButtons = new ArrayList<InlineKeyboardButton>();
        var keyboardButtons = new ArrayList<ArrayList<InlineKeyboardButton>>();
        for (int i = 1; i < ListeningButtons.values().length + 1; i++) {
            keyboardRowButtons.add(InlineKeyboardButton.builder().text(ListeningButtons.values()[i - 1].text)
                    .callbackData(ListeningButtons.values()[i - 1].name()).build());
            if (i % 3 == 0) {
                keyboardButtons.add(keyboardRowButtons);
                keyboardRowButtons = new ArrayList<>();
            }
        }
        return InlineKeyboardMarkup.builder().keyboard(keyboardButtons).build();
    }

    public static String getAnswerByName(String name) {
        return Arrays.stream(ListeningButtons.values()).filter(b -> b.name().equals(name)).findFirst().get().getAnswer();
    }

}
