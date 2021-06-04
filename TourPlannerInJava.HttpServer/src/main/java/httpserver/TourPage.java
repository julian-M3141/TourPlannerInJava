package httpserver;

import at.technikum.businessLayer.EnergyConsumption;
import at.technikum.businessLayer.LogData;
import at.technikum.models.Tour;

import java.time.format.DateTimeFormatter;

public class TourPage extends Webpage{
    private Tour selectedTour = null;
    public TourPage(int id){
        var opt = manager.get(id);
        if(opt.isPresent()){
            selectedTour = opt.get();
        }else{
            throw new IllegalArgumentException("ID not found");
        }
    }
    @Override
    protected String getTitle() {
        return selectedTour.getName();
    }

    @Override
    protected String getBody() {
        StringBuilder content = new StringBuilder();
        content.append("\t\t<h2>").append(selectedTour.getName()).append("</h2>\n");
        content.append("\t\t<img src=\"/pics/").append(selectedTour.getImage()).append("\">\n");
        content.append("\t\t<table class=\"table table-hover\">\n");
        content.append("\t\t\t<thead>\n");
        content.append("\t\t\t\t<tr>\n");
        content.append("\t\t\t\t\t<th>Date</th>\n");
        content.append("\t\t\t\t\t<th>Total time</th>\n");
        content.append("\t\t\t\t\t<th>Rating</th>\n");
        content.append("\t\t\t\t\t<th>Weather</th>\n");
        content.append("\t\t\t\t\t<th>Sport</th>\n");
        content.append("\t\t\t\t\t<th>Steps</th>\n");
        content.append("\t\t\t\t\t<th>Pace</th>\n");
        content.append("\t\t\t\t\t<th>Speed</th>\n");
        content.append("\t\t\t\t\t<th>Energy</th>\n");
        content.append("\t\t\t\t</tr>\n");
        content.append("\t\t\t</thead>\n");
        content.append("\t\t\t<tbody>\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        for(var log : selectedTour.getLogs()) {
            content.append("\t\t\t\t<tr>\n");
            content.append("\t\t\t\t\t<td>").append(log.getTime().format(formatter)).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append(String.format("%d:%02d",log.getTimeinminutes()/60,log.getTimeinminutes()%60)).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append("*".repeat(log.getRating())).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append(log.getWeather()).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append(log.getSport()).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append(log.getSteps()).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append(String.format("%.2f min/km", LogData.getPace(log))).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append(String.format("%.2f km/h", LogData.getAverageSpeed(log))).append("</td>\n");
            content.append("\t\t\t\t\t<td>").append(String.format("%.2f kcal", EnergyConsumption.getCalories(log))).append("</td>\n");
            content.append("\t\t\t\t</tr>\n");
        }
        content.append("\t\t\t</tbody>\n");
        content.append("\t\t</table>\n");
        return content.toString();
    }
}
