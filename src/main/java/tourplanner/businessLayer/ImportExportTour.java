package tourplanner.businessLayer;

import org.json.JSONArray;
import org.json.JSONObject;
import tourplanner.dataAccess.FileHandler;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ImportExportTour {
    public Tour importTour(String filename) throws FileNotFoundException {
        String content = FileHandler.read("",filename);
        return toTour(new JSONObject(content));
    }


    public void export(Tour tour, String filename) throws IOException {
        //do some coding here...
        FileHandler.saveNewFile("",filename,tour.toJSON().toString());
    }

    private Tour toTour(JSONObject obj){
        JSONArray j_logs = obj.getJSONArray("logs");
        ArrayList<Log> logs = new ArrayList<>();
//        for(Object o : j_logs){
//            logs.add(toLog((JSONObject) o));
//        }
        for(int i=0;i<j_logs.length();++i){
            logs.add(toLog(j_logs.getJSONObject(i)));
        }
        return new Tour.Builder()
                .setId(-1)
                .setName(obj.getString("tourname"))
                .setDescription(obj.getString("description"))
                .setDistance(obj.getInt("distance"))
                .setFinish(obj.getString("finish"))
                .setStart(obj.getString("start"))
                .setImage(obj.getString("image"))
                .setLogs(logs)
                .build();

    }

    private Log toLog(JSONObject o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return new Log.Builder()
                .setId(-1)
                .setTime(LocalDateTime.parse(o.getString("date"), formatter))
                .setTimeinminutes(o.getInt("time"))
                .setRating(o.getInt("rating"))
                .setDistance(o.getInt("distance"))
                .setWeather(Weather.valueOf(o.getString("weather")))
                .setWeight(o.getInt("weight"))
                .setHeight(o.getInt("height"))
                .setSport(Sport.valueOf(o.getString("sport")))
                .setSteps(o.getInt("steps"))
                .build();
    }
}