import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.Properties;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String CONFIG_FILE_PATH  = "config.properties";
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(Main.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH));
       String dataServerHost =properties.getProperty("data.host");
        String token = properties.getProperty("bot.token");
        String botUsername=properties.getProperty("bot.username");
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(token, dataServerHost, botUsername));
            LOGGER.info("Bot initiated and ready to dispatch.");
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
