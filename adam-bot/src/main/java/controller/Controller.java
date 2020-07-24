package controller;

import controller.command.CommandFactory;
import exception.DataServerConnectionException;
import model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
        return CommandFactory.defineCommand(inputMessage).answer(inputMessage, model);
    }

    public String defineNoneCommandAnswer(String message) {
        String description;
        try {
            description = getCityDescription(message);
        } catch (DataServerConnectionException e) {
            LOGGER.error("A data server error occurred.", e);
            return DATA_SERVER_CONNECTION_ERROR_MSG;
        }
        return description;
    }

    public String getCityDescription(String name) {
        return model.getCityDescription(name);
    }


    public void closeModel(){
        try {
            model.close();
        } catch (IOException e) {
            LOGGER.error("Failed to close model.", e);
        }
    }
}
