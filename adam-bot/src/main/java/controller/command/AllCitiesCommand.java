package controller.command;

import model.Model;
import model.entity.City;

import java.util.List;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class AllCitiesCommand implements Command{
    private static final String EMPTY_LIST_MSG = "Пока в мой базе нет ни одного города.";
    private static final String MESSAGE_TITLE = "Вот всё, что я знаю:\n";
    @Override
    public String answer(String inputMessage, Model model) {
        List<City> cityList = model.getAllCities();
        if (cityList.isEmpty()){
            return EMPTY_LIST_MSG;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(MESSAGE_TITLE);
        for (City city : cityList){
            builder.append(city.getName()).append(": ").append(city.getDescription()).append("\n");
        }
        return builder.toString();
    }
}
