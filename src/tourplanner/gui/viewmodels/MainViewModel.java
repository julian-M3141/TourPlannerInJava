package tourplanner.gui.viewmodels;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tourplanner.businessLayer.FileHandler;
import tourplanner.businessLayer.TourReport;
import tourplanner.dataAccess.DataAccessObject;
import tourplanner.gui.controller.FormController;
import tourplanner.gui.controller.LogFormController;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainViewModel {
    private final StringProperty search = new SimpleStringProperty("");



    private ObjectProperty<Image> image = new SimpleObjectProperty<>();

    private final StringProperty tourname = new SimpleStringProperty("");

    private final StringProperty finish = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private Tour selectedTour;
    private DataAccessObject dao = DataAccessObject.Instance();
    private FileHandler handler = new FileHandler();
    private TourReport report = new TourReport();




    public String getSearch() {
        return search.get();
    }
    public StringProperty searchProperty() {
        return search;
    }
    public ObjectProperty<Image> imageProperty() {
        return image;
    }
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

    //constructor
    public MainViewModel(){
        ArrayList<String> list = new ArrayList<>();
        for(Tour i : data){
            list.add(i.getTourname());
        }
        names =  FXCollections.observableArrayList(list);
        selectedTour = data.get(0);
        setTourData();
        setLogs();
        try {
            image.set(new Image(new FileInputStream("test.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setLogs() {
        logs.clear();
        logs.addAll(selectedTour.getLogs());
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
        data.clear();
        data.addAll(dao.search(search.get()));
        System.out.println("search...");
    }

    public void edit(){
        System.out.println("edit...");
    }

    public void select(Object selectedItem){
        if(selectedItem != null) {
            selectedTour = (Tour) selectedItem;
            setTourData();
            setLogs();
            System.out.println("clicked on " + ((Tour) selectedItem).getTourname());
        }
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

    public void deleteLog(Object log) {
        dao.delete(selectedTour,(Log)log );
        setLogs();
        System.out.println("delete Log");
    }

    public void print(){
        try {
            report.print(selectedTour);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void export(){
        try {
            handler.export(selectedTour,selectedTour.getTourname().replace(' ','_')+".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteTour(Object tour) {
        dao.deleteTour((Tour) tour);
        refresh();
    }

    public void refresh(){
        data.clear();
        data.addAll(dao.getAllTours());
        try{
            //maybe change to last edited
            select(data.get(0));
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public void newTour(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../views/tourForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Neue Tour erstellen");
            stage.setScene(new Scene(root,600,400));
            stage.show();
            System.out.println("new window");
            stage.setOnHiding(e ->{
                refresh();
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateTour(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/tourForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tour bearbeiten");
            stage.setScene(new Scene(loader.load(),600,400));

            FormController controller = loader.getController();
            controller.initData(selectedTour);

            stage.show();
            stage.setOnHiding(e ->{
                refresh();
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addLog() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/logForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Neue Logeintrag erstellen");
            stage.setScene(new Scene(loader.load(),600,395));

            LogFormController controller = loader.getController();
            controller.initTour(selectedTour);
            stage.show();
            stage.setOnHiding(e ->{
                setLogs();
            });
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Add log");
    }

    public void updateLog(Object log) {
        if(log != null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/logForm.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Neue Logeintrag erstellen");
                stage.setScene(new Scene(loader.load(), 600, 395));

                LogFormController controller = loader.getController();
                controller.initTour(selectedTour);
                controller.initData((Log)log);
                stage.show();
                stage.setOnHiding(e -> {
                    setLogs();
                });
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            addLog();
        }
        System.out.println("Update log");
    }
}
