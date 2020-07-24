package controller.command;

import model.Model;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class HelpCommand implements Command{
    private static final String COMMAND_LIST = "Вот список моих команд:\n" +
            "/allcities -> вывести все города и их описания.\n"+
            "/testconnection -> проверить соединение с data-сервером.";
    @Override
    public String answer(String inputMessage, Model model) {
        return COMMAND_LIST;
    }
}
