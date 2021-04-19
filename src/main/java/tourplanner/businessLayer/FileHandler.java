package tourplanner.businessLayer;

import org.json.JSONArray;
import org.json.JSONObject;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    public Tour importTour(String filename) throws FileNotFoundException {
        File myObj = new File(filename);
        Scanner myReader = null;
        myReader = new Scanner(myObj);
        StringBuilder sb = new StringBuilder();
        while (myReader.hasNextLine()) {
            sb.append(myReader.nextLine());
        }
        return toTour(new JSONObject(sb.toString()));
    }


    public void export(Tour tour, String filename) throws IOException {
        //do some coding here...
        File newFile = new File(filename);
        //throws IOException if file already exists
        if(newFile.createNewFile()){
            FileWriter writer = new FileWriter(filename);
            writer.write(toJSON(tour).toString());
            writer.close();
        }
    }
    private JSONObject toJSON(Tour tour){
        JSONObject obj = new JSONObject();
        obj.put("tourname",tour.getName());
        obj.put("distance",tour.getDistance());
        obj.put("start",tour.getStart());
        obj.put("finish",tour.getFinish());
        obj.put("description",tour.getDescription());
        obj.put("image",tour.getImage());
        JSONArray logs = new JSONArray();
        for(Log log : tour.getLogs()){
            logs.put(toJSON(log));
        }
        obj.put("logs",logs);
        return obj;
    }

    private JSONObject toJSON(Log log) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        JSONObject obj = new JSONObject();
        obj.put("date",log.getTime().format(format));
        obj.put("time",log.getTimeinminutes());
        obj.put("rating",log.getRating());
        obj.put("distance",log.getDistance());
        obj.put("weather",log.getWeather().toString());
        obj.put("weight",log.getWeight());
        obj.put("height",log.getHeight());
        obj.put("sport",log.getSport().toString());
        obj.put("steps",log.getSteps());
        return obj;
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
