package dev.asisee.cookbot;

import dev.asisee.cookbot.bot.CookBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

  public static void main(final String[] args) {
    ApiContextInitializer.init();

    final TelegramBotsApi botsApi = new TelegramBotsApi();

    try {
      botsApi.registerBot(new CookBot());
    } catch (final TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
