import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {
    public static final String BOT_TOKEN = "5968666074:AAEnRe8Fk3W4qx4hGuuV0-SjYHPLqFsOgls"; // Telegram Bot Token
    public static final String BOT_USERNAME = "NASAPicturesDownloader_bot"; // Telegram Bot Username
    public static final String URL = "https://api.nasa.gov/planetary/apod?api_key="; // NASA API KEY NEEDED

    public TelegramBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")) {
                SendMessage msg = new SendMessage();
                msg.setChatId(update.getMessage().getChatId());
                msg.setText("Привет! Я-бот, который по команде \"NASA\" вышлет тебе фото.\n" +
                        "Hey! I'm a bot that sends you a photo on NASA's command.");
                sendMessage(msg);
            }

            if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equalsIgnoreCase("nasa")) {
                SendMessage msg = new SendMessage();
                msg.setChatId(update.getMessage().getChatId());
                try {
                    msg.setText(Utils.getHdUrl(URL));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sendMessage(msg);
            }
        }
    }

    private void sendMessage(SendMessage msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
