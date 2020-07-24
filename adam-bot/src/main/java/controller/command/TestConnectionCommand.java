package controller.command;

import model.Model;

/**
 * This is a simple controller.command to test availability of a data-server while debugging and testing.
 * TODO: should be deleted on production stage
 *
 * @author ArtSCactus
 * @version 1.0
 */
public class TestConnectionCommand implements Command {
    private static final String TRUE_MSG = "Источник моих познаний доступен.";
    private static final String FALSE_MSG = "Источник моих познаний недоступен.";

    @Override
    public String answer(String inputMessage, Model model) {
        boolean isServerOk = model.testConnectionToDataServer();
        return isServerOk ? TRUE_MSG : FALSE_MSG;
    }
}
