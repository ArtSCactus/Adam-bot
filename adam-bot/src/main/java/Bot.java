import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author ArtSCactus
 * @version 1.1
 */
public class Bot extends TelegramLongPollingCommandBot {
    private static final String UNKNOWN_MESSAGE_TYPE = "Извините, но я пока не знаю как на это реагировать.";
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);
    private final String BOT_TOKEN;
    private final String BOT_USERNAME;
    private Controller controller;

    public Bot(String botToken, String datasourceHost, String botUsername) {
        controller = new Controller(datasourceHost);
        BOT_TOKEN = botToken;
        BOT_USERNAME = botUsername;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        try {
            String message = update.getMessage().getText();
            String answer = controller.defineNoneCommandAnswer(message);
            LOGGER.info("An incoming message from " + update.getMessage().getFrom().getUserName()
                    + "\n with text: " + message + ".\nDefined answer: " + answer);
            sendMsg(update.getMessage().getChatId().toString(), answer);
        } catch (NullPointerException e) { // when from chat coming emoji, the message is null.
            LOGGER.warn("An unknown message was received. Answering as unknown message type. \nUpdate object:\n" + update.toString());
            sendMsg(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE_TYPE);
        }
    }


    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            try {
                String message = update.getMessage().getText();
                if (!message.startsWith("/")) {
                    processNonCommandUpdate(update);
                } else {
                    String answer = controller.defineAnswer(message);
                    LOGGER.info("An incoming message from " + update.getMessage().getFrom().getUserName()
                            + "\n with text: " + message + ".\nDefined answer: " + answer);
                    sendMsg(update.getMessage().getChatId().toString(), answer);
                }

            } catch (NullPointerException e) { // when from chat coming emoji, the message is null.
                LOGGER.error("An unknown message was received. Answering as unknown message type. \nUpdate object:\n" + update.toString());
                sendMsg(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE_TYPE);
            }
        }
    }


    /**
     * Message dispatcher
     *
     * @param chatId id of current chat
     * @param s      user message.
     */
    private void sendMsg(String chatId, String s) {
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
        return BOT_USERNAME;
    }

    @Override
    protected void finalize() throws Throwable {
        controller.closeModel();
    }
}
