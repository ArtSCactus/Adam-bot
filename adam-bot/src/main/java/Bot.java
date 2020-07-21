import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class Bot extends TelegramLongPollingBot {
    public String getBotToken() {
        return "token";
    }

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    /**
     * Message dispatcher
     *
     * @param chatId id of current chat
     * @param s user message.
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            // normal Logger
            System.out.println("Fatal error "+ e.getMessage());
        }
    }
    public String getBotUsername() {
        return "ArtSCactus";
    }


}
