package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tour {

    //private member variables
    private int id;
    private String name;
    private String description;
    private String start;
    private String finish;
    private int distance;
    private String image;
    private ArrayList<Log> logs;

    //constructor
    public Tour(int id, String name, String description, String start, String finish, int distance, String image, ArrayList<Log> logs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.finish = finish;
        this.distance = distance;
        this.image = image;
        this.logs = logs;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }


    //builder pattern
    public static class Builder {
        private int id=-1;

        private String name=null;
        private String description=null;
        private String start=null;
        private String finish=null;
        private int distance=-1;
        private String image=null;
        private ArrayList<Log> logs=null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder setFinish(String finish) {
            this.finish = finish;
            return this;
        }

        public Builder setStart(String start) {
            this.start = start;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setImage(String image) {
            this.image = image;
            return this;
        }
        public Builder setLogs(ArrayList<Log> logs) {
            this.logs = logs;
            return this;
        }

        public Tour build(){
            if(name == null || description == null || start == null || finish == null || distance < 0){
                throw new IllegalArgumentException("All instances have to be set!");
            }
            if(logs==null)
                logs = new ArrayList<Log>();
            return new Tour(id,name,description,start,finish,distance,image,logs);
        }

    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("tourname",getName());
        obj.put("distance",getDistance());
        obj.put("start",getStart());
        obj.put("finish",getFinish());
        obj.put("description",getDescription());
        obj.put("image",getImage());
        JSONArray logs = new JSONArray();
        for(Log log : getLogs()){
            logs.put(log.toJSON());
        }
        obj.put("logs",logs);
        return obj;
    }

}
