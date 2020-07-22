package command;

import model.Model;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class UnknownCommand implements Command {
    private static final String UNKNOWN_COMMAND_MSG = "Извините, но такой команды я не знаю.";
    @Override
    public String answer(String inputMessage, Model model) {
        return UNKNOWN_COMMAND_MSG;
    }
}
