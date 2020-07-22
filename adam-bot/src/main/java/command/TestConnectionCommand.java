package command;

import model.Model;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class TestConnectionCommand implements Command {
    @Override
    public String answer(String inputMessage, Model model) {
        boolean isServerOk = model.testConnectionToDataServer();
        return isServerOk? "Источник моих познаний доступен.": "Источник моих познаний недоступен.";
    }
}
