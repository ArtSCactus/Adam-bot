package controller;

import controller.command.CommandFactory;
import exception.DataServerConnectionException;
import model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ArtSCactus
 * @version 1.1
 */
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final String DATA_SERVER_CONNECTION_ERROR_MSG = "Упс... видимо, источник моих познаний сейчас" +
            " недоступен.";
    private Model model;

    public Controller(String dataServerHost) {
        model = new Model(dataServerHost);
    }

    public String defineAnswer(String inputMessage) {
        if (inputMessage.startsWith("/")) {
            return CommandFactory.defineCommand(inputMessage).answer(inputMessage, model);
        } else {
            String description;
            try {
                description = getCityDescription(inputMessage);
            } catch (DataServerConnectionException e) {
                LOGGER.error("A data server error occurred.", e);
                return DATA_SERVER_CONNECTION_ERROR_MSG;
            }
            return description;

        }
    }

    public String getCityDescription(String name) {
        return model.getCityDescription(name);
    }
}
