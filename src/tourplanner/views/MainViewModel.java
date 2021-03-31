package tourplanner.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;

public class MainViewModel {
    private final StringProperty currentTourname = new SimpleStringProperty("");
    private final StringProperty currentDistance = new SimpleStringProperty("");

    private final StringProperty tourname = new SimpleStringProperty("");
    private final StringProperty finish = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private Tour selectedTour;

    private void setTourData(){
        tourname.set(selectedTour.tournameProperty().get());
        finish.set(selectedTour.finishProperty().get());
        start.set(selectedTour.startProperty().get());
        distance.set(selectedTour.distanceProperty().get());
        description.set(selectedTour.descriptionProperty().get());
    }


    private final ObservableList<Tour> data = FXCollections.observableArrayList(
            new Tour("Lange tour","200km", "London" , "Peking" , "long description ... ", new ArrayList<Log>(){{
                add(new Log("12.1.2021", "12h", "5 stars"));
                add(new Log("18.1.2021", "11h", "4.5 stars"));
            }}),
            new Tour("Kurze tour","10km", "Peking" , "London" ,"short description", new ArrayList<Log>(){{
                add(new Log("15.1.2021", "1h5min", "3 stars"));
            }})
    );

    private final ObservableList<Log> logs = FXCollections.observableArrayList();
    private final ObservableList<String> names; // = FXCollections.observableArrayList("Lange Tour","Kurze Tour");
    public MainViewModel(){
        ArrayList<String> list = new ArrayList<>();
        for(Tour i : data){
            list.add(i.getTourname());
        }
        names =  FXCollections.observableArrayList(list);
        selectedTour = data.get(0);
        setTourData();
        setLogs();
    }

    private void setLogs() {
        while(logs.size()>0)
            logs.remove(0);
        for(Log i: selectedTour.getLogs())
            logs.add(i);

    }

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
        return logs;
    }
    public void search(){
        System.out.println("search...");
    }

    public void edit(){
        System.out.println("edit...");
    }

    public void select(Object selectedItem){
        System.out.println("clicked on " + (String)selectedItem);
        for(Tour i: data){
            if(((String)selectedItem).equals(i.getTourname())){
                selectedTour = i;
                System.out.println("Found!"+ i.getTourname());
                setTourData();
                break;
            }
        }
        setLogs();
    }

    public StringProperty tournameProperty() {
        return tourname;
    }

    public StringProperty finishProperty() {
        return finish;
    }

    public StringProperty startProperty() {
        return start;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void deleteLog() {
        System.out.println("delete Log");
    }

    public void addLog() {
        System.out.println("Add log");
    }
}
