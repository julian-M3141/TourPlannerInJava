package gui.viewmodels.mainwindow;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gui.TourButtonListener;
import models.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {

    private final List<TourButtonListener> tourButtonListeners = new ArrayList<>();

    private final Property<Tour> tour = new SimpleObjectProperty<>();

    private final ChangeListener<Tour> changeListener = (observableValue, oldtour,newtour) -> {
        if(newtour!=null)
            select(newtour);
    };

    private final ObservableList<Tour> data = FXCollections.observableArrayList();

    public ChangeListener<Tour> getChangeListener() {
        return changeListener;
    }

    public void addSelectionChangedListener(ChangeListener<Tour> changeListener){
        tour.addListener(changeListener);
    }

    public void setTours(List<Tour> tours){
        data.clear();
        data.addAll(tours);
    }

    public ObservableList<Tour> getData() {
        return data;
    }

    public void addTourButtonListener(TourButtonListener listener){
        tourButtonListeners.add(listener);
    }

    public void select(Tour selectedItem) {
        tour.setValue(selectedItem);
    }

    public void newTour() {
        for(var listener : tourButtonListeners){
            listener.createNewTour();
        }
    }

    public void deleteTour() {
        for(var listener : tourButtonListeners){
            listener.deleteTour();
        }
    }

    public void updateTour() {
        for(var listener : tourButtonListeners){
            listener.updateTour();
        }
    }
}
