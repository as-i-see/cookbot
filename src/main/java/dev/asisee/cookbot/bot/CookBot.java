package dev.asisee.cookbot.bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CookBot
  extends TelegramLongPollingBot {

  @Override
  public void onUpdateReceived(
    Update update
  ) {// We check if the update has a message and the message has text

    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message = new SendMessage(

      ).// Create a SendMessage object with mandatory fields
      setChatId(update.getMessage().getChatId()).setText(
        update.getMessage().getText()
      ).setReplyMarkup(getRootMenu());
      try {
        execute(message); // Call method to send the message
      } catch(TelegramApiException e) {
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
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    // Create the keyboard (list of keyboard rows)
    List<KeyboardRow> keyboard = new ArrayList<>();
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

