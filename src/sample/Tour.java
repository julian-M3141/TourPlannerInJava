package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Tour {
    private final StringProperty tourname;
    private final StringProperty distance;
    private final StringProperty start;
    private final StringProperty finish;
    private final ArrayList<Log> logs;

    public Tour(String username, String points,String start, String finish){
        this.tourname = new SimpleStringProperty(username);
        this.distance = new SimpleStringProperty(points);
        this.finish = new SimpleStringProperty(finish);
        this.start = new SimpleStringProperty(start);
        this.logs = new ArrayList<Log>();
    }

    public Tour(String username, String points,String start, String finish, ArrayList<Log> logs){
        this.tourname = new SimpleStringProperty(username);
        this.distance = new SimpleStringProperty(points);
        this.finish = new SimpleStringProperty(finish);
        this.start = new SimpleStringProperty(start);
        this.logs = logs;
    }

    public Tour(StringProperty tourname, StringProperty distance,StringProperty start, StringProperty finish, ArrayList<Log> logs) {
        this.tourname = tourname;
        this.distance = distance;
        this.finish = finish;
        this.start = start;
        this.logs = logs;
    }

    public Tour(StringProperty tourname,StringProperty start, StringProperty finish, StringProperty distance) {
        this.tourname = tourname;
        this.distance = distance;
        this.finish = finish;
        this.start = start;
        this.logs = new ArrayList<Log>();
    }

    public String getDistance() {
        return distance.get();
    }

    public void setDistance(String distance){
        this.distance.set(distance);
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public String getTourname() {
        return tourname.get();
    }

    public void setTourname(String tourname){
        this.tourname.set(tourname);
    }

    public StringProperty tournameProperty() {
        return tourname;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public String getStart() {
        return start.get();
    }

    public StringProperty startProperty() {
        return start;
    }

    public String getFinish() {
        return finish.get();
    }

    public StringProperty finishProperty() {
        return finish;
    }
}
