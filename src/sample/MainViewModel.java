package sample;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MainViewModel {
    private final StringProperty currentTourname = new SimpleStringProperty("");
    private final StringProperty currentDistance = new SimpleStringProperty("");
    private final Tour selectedTour = new Tour("Lange tour","200km", "London" , "Peking" ,new ArrayList<Log>(){{
        add(new Log("12.1.2021", "12h", "5 stars"));
        add(new Log("18.1.2021", "11h", "4.5 stars"));
    }});
    private final ObservableList<Tour> data = FXCollections.observableArrayList(
            new Tour("lange tour","200km", "London" , "Peking" , new ArrayList<Log>(){{
                add(new Log("12.1.2021", "12h", "5 stars"));
                add(new Log("18.1.2021", "11h", "4.5 stars"));
            }}),
            new Tour("kurze tour","10km", "London" , "Peking" , new ArrayList<Log>(){{
                add(new Log("15.1.2021", "1h5min", "3 stars"));
            }})
    );
    private final ObservableList<String> names = FXCollections.observableArrayList("Lange Tour","Kurze Tour");

    public String getCurrentDistance() {
        return currentDistance.get();
    }

    public StringProperty currentDistanceProperty() {
        return currentDistance;
    }

    public String getCurrentTourname() {
        return currentTourname.get();
    }

    public StringProperty currentTournameProperty() {
        return currentTourname;
    }

    public ObservableList<Tour> getData() {
        return data;
    }

    public void saveData(){

        if(!currentTourname.get().isEmpty() && !currentDistance.get().isEmpty()) {
            //data.add(new Tour(currentTourname.get(), currentDistance.get()));
            currentDistance.set("");
            currentTourname.set("");
        }

    }

    public ObservableList<String> getNames() {
        return names;
    }

    public Tour getTour() {
        return selectedTour;
    }
    public ObservableList<Log> getLogs() {
        return FXCollections.observableArrayList(selectedTour.getLogs());
    }
}
