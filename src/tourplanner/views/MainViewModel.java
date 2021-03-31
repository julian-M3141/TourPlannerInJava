package tourplanner.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tourplanner.dataAccess.DataAccessObject;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;

public class MainViewModel {
    public String getSearch() {
        return search.get();
    }

    public StringProperty searchProperty() {
        return search;
    }

    private final StringProperty search = new SimpleStringProperty("");

    private final StringProperty tourname = new SimpleStringProperty("");
    private final StringProperty finish = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private Tour selectedTour;

    private DataAccessObject dao = new DataAccessObject();

    private void setTourData(){
        tourname.set(selectedTour.tournameProperty().get());
        finish.set(selectedTour.finishProperty().get());
        start.set(selectedTour.startProperty().get());
        distance.set(selectedTour.distanceProperty().get());
        description.set(selectedTour.descriptionProperty().get());
    }


    private ObservableList<Tour> data = FXCollections.observableArrayList(dao.getAllTours());

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

    public ObservableList<Tour> getData() {
        return data;
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
        data = FXCollections.observableArrayList(dao.search(search.get()));
        System.out.println("search...");
    }

    public void edit(){
        System.out.println("edit...");
    }

    public void select(Object selectedItem){
        selectedTour = (Tour) selectedItem;
        setTourData();
        setLogs();
        System.out.println("clicked on " + ((Tour) selectedItem).getTourname());
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
