package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tour {
    private final StringProperty tourname;
    private final StringProperty distance;

    public Tour(String username, String points){
        this.tourname = new SimpleStringProperty(username);
        this.distance = new SimpleStringProperty(points);
    }

    public Tour(StringProperty tourname, StringProperty distance) {
        this.tourname = tourname;
        this.distance = distance;
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
}
