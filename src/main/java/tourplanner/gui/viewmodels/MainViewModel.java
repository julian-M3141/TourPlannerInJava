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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tourplanner.businessLayer.report.BasicTourReport;
import tourplanner.businessLayer.ImportExportTour;
import tourplanner.businessLayer.manager.AppManagerFactory;
import tourplanner.businessLayer.manager.IAppManger;
import tourplanner.businessLayer.report.SummarizeReport;
import tourplanner.gui.controller.FormController;
import tourplanner.gui.controller.LogFormController;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class MainViewModel {

    private final StringProperty search = new SimpleStringProperty("");
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
    private final StringProperty tourname = new SimpleStringProperty("");
    private final StringProperty finish = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private Tour selectedTour;
    private final IAppManger manager = AppManagerFactory.getManager();
    private final ImportExportTour handler = new ImportExportTour();
    private final BasicTourReport report = new BasicTourReport();
    private final SummarizeReport summarizeReport = new SummarizeReport();

    private final Logger logger;

    private final ObservableList<Tour> data = FXCollections.observableArrayList(manager.getAll());

    private final ObservableList<Log> logs = FXCollections.observableArrayList();

    //constructor
    public MainViewModel(){
        //set tour and data
        if(data.size()>0) {
            selectedTour = data.get(0);
            setTourData();
            setLogs();
        }
        logger = LogManager.getLogger(MainViewModel.class);
    }

    private void setTourData(){
        tourname.set(selectedTour.getName());
        finish.set(selectedTour.getFinish());
        start.set(selectedTour.getStart());
        distance.set(selectedTour.getDistance() + " km");
        description.set(selectedTour.getDescription());
        try {
            image.set(new Image(new FileInputStream("pics/"+selectedTour.getImage())));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            logger.error("No image available for tour '"+tourname.get() +"', set default image");
            try {
                image.set(new Image(Objects.requireNonNull(MainViewModel.class.getClassLoader().getResourceAsStream("pics/test.jpeg"))));
            }catch (NullPointerException n){
                logger.fatal("Cannot find default image");
            }
        }
    }

    public StringProperty searchProperty() {
        return search;
    }
    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    private void setLogs() {
        logs.clear();
        logs.addAll(selectedTour.getLogs());
    }

    public ObservableList<Tour> getData() {
        return data;
    }

    public Tour getTour() {
        return selectedTour;
    }
    public ObservableList<Log> getLogs() {
        return logs;
    }
    public void search(){
        data.clear();
        data.addAll(manager.search(search.get()));
    }

    public void select(Object selectedItem){
        if(selectedItem != null) {
            selectedTour = (Tour) selectedItem;
            setTourData();
            setLogs();
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
        manager.delete(selectedTour,(Log)log );
        setLogs();
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
            handler.export(selectedTour,selectedTour.getName().replace(' ','_')+".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void importFile(String filename) {
        try {
            manager.save(handler.importTour(filename));
            refresh();
            select(manager.getLast());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteTour(Object tour) {
        manager.delete((Tour) tour);
        refresh();
    }

    public void refresh(){
        data.clear();
        data.addAll(manager.getAll());
        Optional<Tour> t = manager.get(selectedTour.getId());
        t.ifPresent(this::select);
    }

    public void newTour(){
        Parent root;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/tourForm.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Neue Tour erstellen");
            stage.setScene(new Scene(root,600,400));
            stage.show();
            System.out.println("new window");
            stage.setOnHiding(e ->{
                refresh();
                select(manager.getLast());
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateTour(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tourForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tour bearbeiten");
            stage.setScene(new Scene(loader.load(),600,400));

            FormController controller = loader.getController();
            controller.initData(selectedTour);

            stage.show();
            stage.setOnHiding(e ->{
                refresh();
                //select(selectedTour);
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addLog() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/logForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Neue Logeintrag erstellen");
            stage.setScene(new Scene(loader.load(),600,395));

            LogFormController controller = loader.getController();
            controller.initTour(selectedTour);
            stage.show();
            stage.setOnHiding(e -> {
                refresh();
                setLogs();
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateLog(Object log) {
        if(log != null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/logForm.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Neue Logeintrag erstellen");
                stage.setScene(new Scene(loader.load(), 600, 395));

                LogFormController controller = loader.getController();
                controller.initTour(selectedTour);
                controller.initData((Log)log);
                stage.show();
                stage.setOnHiding(e -> {
                    refresh();
                    setLogs();
                });
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            addLog();
        }
    }

    public void summarizeReport() {
        try {
            summarizeReport.print(selectedTour);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
    }
}
