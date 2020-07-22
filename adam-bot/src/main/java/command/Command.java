package command;

import model.Model;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public interface Command {
    String answer(String inputMessage, Model model);
}
