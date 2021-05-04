package tourplanner.gui.viewmodels;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import tourplanner.businessLayer.manager.AppManagerFactory;
import tourplanner.businessLayer.manager.IAppManger;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

public class LogFormViewModel {
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty time = new SimpleStringProperty("");
    private final StringProperty hours = new SimpleStringProperty("");
    private final StringProperty minutes = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty weight = new SimpleStringProperty("");
    private final StringProperty height = new SimpleStringProperty("");
    private final StringProperty sport = new SimpleStringProperty("");
    private final StringProperty weather = new SimpleStringProperty("");
    private final StringProperty steps = new SimpleStringProperty("");
    private boolean update = false;
    private final IAppManger manager = AppManagerFactory.getManager();
    private final ObservableList<String> ratinglist = FXCollections.observableList(Arrays.asList("*","**","***","****","*****"));
    private final ObservableList<String> weatherlist = FXCollections.observableList(Arrays.asList("Sunny","Rain","Cloudy","Foggy","Snowfall"));
    private final ObservableList<String> sportslist = FXCollections.observableList(Arrays.asList("Running","Bicycle","Hiking"));
    private Tour editedTour = null;

    private Log log = null;


    public BooleanBinding isEmpty(){
        return date.isNull().or(hours.isEmpty()).or(minutes.isEmpty()).or(time.isEmpty())
                .or(rating.isEmpty()).or(distance.isEmpty()).or(weather.isEmpty())
                .or(sport.isEmpty()).or(weight.isEmpty()).or(height.isEmpty()).or(steps.isEmpty());
    }

    public void addIntegerListener(TextField field){
        field.textProperty().addListener(((observableValue, oldVal,newVal) -> {
            if(!isInteger(newVal)){
                field.setText(oldVal);
            }
        }));
    }

    public void addIntegerRangeListener(TextField field,int start,int finish){
        field.textProperty().addListener(((observableValue, oldVal,newVal) -> {
            if(!isInteger(newVal)){
                field.setText(oldVal);
                return;
            }
            try{
                if(Integer.parseInt(newVal) < start || Integer.parseInt(newVal) > finish){
                    field.setText(oldVal);
                }
            }catch (NumberFormatException | NullPointerException ignored){}
        }));
    }

    public boolean isInteger(String s){
        if(s == null || s.isEmpty()){
            return true;
        }
        try {
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException | NullPointerException ignored){}
        return false;
    }

    public void setTour(Tour tour){
        editedTour = tour;
    }

    public void saveData(){
        if(update){
            updateLog();
            return;
        }
        manager.save(editedTour,new Log.Builder()
                .setTime(LocalDateTime.of(getDate(), LocalTime.of(Integer.parseInt(getHours()),Integer.parseInt(getMinutes()))))
                .setTimeinminutes(Integer.parseInt(getTime()))
                .setRating(Integer.parseInt(getRating()))
                .setDistance(Integer.parseInt(getDistance()))
                .setWeather(Weather.valueOf(getWeather()))
                .setWeight(Integer.parseInt(getWeight()))
                .setHeight(Integer.parseInt(getHeight()))
                .setSport(Sport.valueOf(getSport()))
                .setSteps(Integer.parseInt(getSteps()))
                .build());
    }

    private void updateLog(){
        HashMap<String,String> params = new HashMap<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        params.put("Datum",LocalDateTime.of(getDate(), LocalTime.of(Integer.parseInt(getHours()),Integer.parseInt(getMinutes()))).format(format));
        params.put("Zeit",getTime());
        params.put("Rating",getRating());
        params.put("Distanz",getDistance());
        params.put("Weather",getWeather());
        params.put("Weight",getWeight());
        params.put("Height",getHeight());
        params.put("Sport",getSport());
        params.put("Steps",getSteps());
        if(log != null){
            manager.update(log,params);
        }
    }

    public void setLog(Log log) {
        this.update = true;
        this.log = log;
        date.set(log.getTime().toLocalDate());
        minutes.set(String.valueOf(log.getTime().getMinute()));
        hours.set(String.valueOf(log.getTime().getHour()));
        time.set(String.valueOf(log.getTimeinminutes()));
        rating.set("*".repeat(log.getRating()));
        distance.set(String.valueOf(log.getDistance()));
        weather.set(log.getWeather().toString());
        sport.set(log.getSport().toString());
        steps.set(String.valueOf(log.getSteps()));
        weight.set(String.valueOf(log.getWeight()));
        height.set(String.valueOf(log.getHeight()));
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

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public String getWeight() {
        return weight.get();
    }

    public StringProperty weightProperty() {
        return weight;
    }

    public String getHeight() {
        return height.get();
    }

    public StringProperty heightProperty() {
        return height;
    }

    public String getSport() {
        return sport.get();
    }

    public StringProperty sportProperty() {
        return sport;
    }

    public String getWeather() {
        return weather.get();
    }

    public StringProperty weatherProperty() {
        return weather;
    }

    public String getSteps() {
        return steps.get();
    }

    public StringProperty stepsProperty() {
        return steps;
    }

    public ObservableList<String> getWeatherlist() {
        return weatherlist;
    }

    public ObservableList<String> getSportslist() {
        return sportslist;
    }

    public ObservableList<String> getRatinglist() {
        return ratinglist;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getMinutes() {
        return minutes.get();
    }

    public StringProperty minutesProperty() {
        return minutes;
    }

    public String getHours() {
        return hours.get();
    }

    public StringProperty hoursProperty() {
        return hours;
    }
}
