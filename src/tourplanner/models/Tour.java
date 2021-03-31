package tourplanner.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Tour {
    private String tourname;
    private String distance;
    private String start;
    private String finish;
    private String description;
    private ArrayList<Log> logs;

    public static class Builder {
        private String tourname=null;
        private String distance=null;
        private String start=null;
        private String finish=null;
        private String description=null;
        private ArrayList<Log> logs=null;

        public Builder setTourname(String tourname) {
            this.tourname = tourname;
            return this;
        }

        public Builder setDistance(String distance) {
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

        public Builder setLogs(ArrayList<Log> logs) {
            this.logs = logs;
            return this;
        }
        public Tour build(){
            if(logs==null)
                logs = new ArrayList<Log>();
            return new Tour(tourname,distance,start,description,finish,logs);
        }
    };

    public Tour(String tourname, String distance,String start,String description, String finish){
        this.tourname = tourname;
        this.distance = distance;
        this.finish = finish;
        this.start = start;
        this.description = description;
        this.logs = new ArrayList<Log>();
    }

    public Tour(String tourname, String distance,String start, String finish,String description, ArrayList<Log> logs){
        this.tourname = tourname;
        this.distance = distance;
        this.finish = finish;
        this.start = start;
        this.logs = logs;
        this.description = description;
    }

    public Tour(StringProperty tourname, StringProperty distance,StringProperty start, StringProperty finish,StringProperty description, ArrayList<Log> logs) {
        this.tourname = tourname.get();
        this.distance = distance.get();
        this.finish = finish.get();
        this.start = start.get();
        this.logs = logs;
        this.description = description.get();
    }

    public Tour(StringProperty tourname,StringProperty start, StringProperty finish, StringProperty distance,StringProperty description) {
        this.tourname = tourname.get();
        this.distance = distance.get();
        this.finish = finish.get();
        this.start = start.get();
        this.description = description.get();
        this.logs = new ArrayList<Log>();
    }

    public String getDistance() {
        return distance;
    }

    public StringProperty distanceProperty() {
        return new SimpleStringProperty(distance);
    }

    public String getTourname() {
        return tourname;
    }

    public StringProperty tournameProperty() {
        return new SimpleStringProperty(tourname);
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public String getStart() {
        return start;
    }

    public StringProperty startProperty() {
        return new SimpleStringProperty(start);
    }

    public String getFinish() {
        return finish;
    }

    public StringProperty finishProperty() { return new SimpleStringProperty(finish); }

    public String getDescription() { return description; }

    public StringProperty descriptionProperty() { return new SimpleStringProperty(description); }
}
