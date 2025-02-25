package ru.mas.ktane_bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.mas.ktane_bot.bot.KtaneBot;

@Configuration
public class KtaneBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(KtaneBot ktaneBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(ktaneBot);
        return api;
    }
}
