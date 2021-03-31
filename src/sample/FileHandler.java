package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    public Tour importTour(String filename) throws FileNotFoundException {
        File myObj = new File(filename);
        Scanner myReader = null;
        Tour tour = null;
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
        obj.put("tourname",tour.getTourname());
        obj.put("distance",tour.getDistance());
        obj.put("start",tour.getStart());
        obj.put("finish",tour.getFinish());
        obj.put("description",tour.getDescription());
        JSONArray logs = new JSONArray();
        for(Log log : tour.getLogs()){
            logs.put(toJSON(log));
        }
        obj.put("logs",logs);
        return obj;
    }

    private JSONObject toJSON(Log log) {
        JSONObject obj = new JSONObject();
        obj.put("date",log.getDate());
        obj.put("time",log.getTime());
        obj.put("rating",log.getRating());
        return obj;
    }

    private Tour toTour(JSONObject obj){
        JSONArray j_logs = obj.getJSONArray("logs");
        ArrayList<Log> logs = new ArrayList<Log>();
//        for(Object o : j_logs){
//            logs.add(toLog((JSONObject) o));
//        }
        for(int i=0;i<j_logs.length();++i){
            logs.add(toLog(j_logs.getJSONObject(i)));
        }
        return new Tour.Builder()
                .setTourname(obj.getString("tourname"))
                .setDescription(obj.getString("description"))
                .setDistance(obj.getString("distance"))
                .setFinish(obj.getString("finish"))
                .setStart(obj.getString("start"))
                .setLogs(logs)
                .build();

    }

    private Log toLog(JSONObject o) {
        return new Log(o.getString("date"),o.getString("time"),o.getString("rating"));
    }
}
