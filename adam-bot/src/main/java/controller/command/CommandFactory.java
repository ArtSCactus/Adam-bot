package controller.command;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class CommandFactory {
    private static final String HELP_KEYWORD = "help";
    private static final String ALL_CITIES_KEYWORD = "allcities";
    private static final String TEST_CONNECTION_KEYWORD = "testconnection";
    private static final String KEY_SYMBOL = "/";

    public static Command defineCommand(String inputMsg) {
        switch (inputMsg) {
            case KEY_SYMBOL+HELP_KEYWORD:
                return new HelpCommand();
            case KEY_SYMBOL+ ALL_CITIES_KEYWORD:
                return new AllCitiesCommand();
            case  KEY_SYMBOL+TEST_CONNECTION_KEYWORD:
                return new TestConnectionCommand();
            default:
                return new UnknownCommand();

        }
    }
}
