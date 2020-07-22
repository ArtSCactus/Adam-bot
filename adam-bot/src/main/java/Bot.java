import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author ArtSCactus
 * @version 1.1
 */
public class Bot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);
    private static final String DATA_SERVER_HOST_URL = "http://localhost:8080";
    private static final String BOT_TOKEN = "1073299330:AAG5p4qoY9pTcmEdEnZFqUeR7hmB_OyRhfg";
    private Controller controller;

    public Bot() {
        controller = new Controller(DATA_SERVER_HOST_URL);
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }


    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String answer = controller.defineAnswer(message);
        LOGGER.info("An incoming message from " + update.getMessage().getFrom().getUserName()
                + "\n with text: " + message + ".\nDefined answer: " + answer);
        sendMsg(update.getMessage().getChatId().toString(), answer);
    }

    /**
     * Message dispatcher
     *
     * @param chatId id of current chat
     * @param s      user message.
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOGGER.error("An error occurred while message sending.", e);
        }
    }

    public String getBotUsername() {
        return "ArtSCactus";
    }


}
