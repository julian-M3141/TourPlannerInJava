package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel {
    private final StringProperty currentTourname = new SimpleStringProperty("");
    private final StringProperty currentDistance = new SimpleStringProperty("");
    private final ObservableList<Tour> data = FXCollections.observableArrayList(
            new Tour("lange tour","200km"),
            new Tour("kurze tour","10km")
    );

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
            data.add(new Tour(currentTourname.get(), currentDistance.get()));
            currentDistance.set("");
            currentTourname.set("");
        }

    }
}
