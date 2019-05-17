package dev.asisee.cookbot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CookBot extends TelegramLongPollingBot {
  private static final URI cloudRedisURI =
      URI.create(
          "redis://h:p4426bdca631eb9bff2f2ce99c4799ede68fb59900642e47783781d87a5986cb2@ec2-99-80-37-150.eu-west-1.compute.amazonaws.com:17669");
  static final JedisPool pool = new JedisPool(new JedisPoolConfig(), CookBot.cloudRedisURI);

  @Override
  public void onClosing() {
    super.onClosing();
    CookBot.pool.close();
  }

  @Override
  public void onUpdateReceived(final Update update) {
    //    Message message = update.getMessage();
    if (update.hasMessage() && update.getMessage().hasText()) {
      final StringBuilder stringBuilder = new StringBuilder("You have already sent:\n");
      try (final Jedis jedis = CookBot.pool.getResource()) {
        jedis.zadd(update.getMessage().getChatId().toString(), 0, update.getMessage().getText());
        jedis
            .zrange(update.getMessage().getChatId().toString(), 0, -1)
            .forEach(stringBuilder::append);
      }
      final SendMessage message =
          new SendMessage()
              .setChatId(update.getMessage().getChatId())
              .setText(stringBuilder.toString())
              .setReplyMarkup(this.getRootMenu());
      try {
        this.execute(message); // Call method to send the message
      } catch (final TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String getBotUsername() {
    return "simplecookbot";
  }

  @Override
  public String getBotToken() {
    return "860409472:AAHhq47abH-VXnwIBpddDasGzs-p8RsSQp8";
  }

  private ReplyKeyboardMarkup getRootMenu() {
    final ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    // Create the keyboard (list of keyboard rows)
    final List<KeyboardRow> keyboard = new ArrayList<>();
    // Create a keyboard row
    KeyboardRow row = new KeyboardRow();
    // Set each button, you can also use KeyboardButton objects if you need something else than text
    row.add("Search for recipe");
    // Add the first row to the keyboard
    keyboard.add(row);
    // Create another keyboard row
    row = new KeyboardRow();
    // Set each button for the second line
    row.add("Goods in stock");
    // Add the second row to the keyboard
    keyboard.add(row);
    // Set the keyboard to the markup
    keyboardMarkup.setKeyboard(keyboard);
    return keyboardMarkup;
  }
}
