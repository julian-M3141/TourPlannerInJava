package tourplanner.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Log {
    public void setDate(String date) {
        this.date.set(date);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty rating;

    public Log(StringProperty date, StringProperty time, StringProperty rating) {
        this.date = date;
        this.time = time;
        this.rating = rating;
    }
    public Log(String date, String time, String rating) {
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.rating = new SimpleStringProperty(rating);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getRating() {
        return rating.get();
    }

    public StringProperty ratingProperty() {
        return rating;
    }
}
