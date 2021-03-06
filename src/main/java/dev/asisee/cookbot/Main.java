package dev.asisee.cookbot;

import dev.asisee.cookbot.bot.CookBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

  public static void main(String[] args) {
    ApiContextInitializer.init();

    TelegramBotsApi botsApi = new TelegramBotsApi();

    try {
      botsApi.registerBot(new CookBot());
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
