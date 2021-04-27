package tourplanner.gui.viewmodels;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;
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
    private MainViewModel(){
        //set tour and data
        if(data.size()>0) {
            selectedTour = data.get(0);
            setTourData();
            setLogs();
        }
        logger = LogManager.getLogger(MainViewModel.class);
    }
    private static MainViewModel model;
    public static MainViewModel Instance(){
        if(model==null){
            model = new MainViewModel();
        }
        return model;
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
        search.set("");
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
        refresh();
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

    public void deleteTour() {
        manager.delete(selectedTour);
        refresh();
    }

    public void refresh(){
        data.clear();
        data.addAll(manager.getAll());
        Optional<Tour> t = manager.get(selectedTour.getId());
        t.ifPresent(this::select);
    }

    public void newTour(){
        createTourWindow("/views/tourForm.fxml","Tour erstellen",false);
    }

    public void updateTour(){
        createTourWindow("/views/tourForm.fxml","Tour bearbeiten",true);
    }

    public void addLog() {
        createLogWindow("/views/logForm.fxml","Neuen Logeintrag erstellen",Optional.empty());
    }

    public void updateLog(Object log) {
        if(log != null){
            createLogWindow("/views/logForm.fxml","Logeintrag updaten",Optional.of((Log)log));
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
    private Pair<FXMLLoader,Stage> createWindow(String resource,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(loader.load(), 600, 400));
        return new Pair<>(loader,stage);
    }

    private void createLogWindow(String resource, String title, Optional<Log> log){
        try{
            var gui = createWindow(resource,title);
            LogFormController controller = gui.getKey().getController();
            controller.initTour(selectedTour);
            log.ifPresent(controller::initData);
            gui.getValue().show();
            gui.getValue().setOnHiding(e -> {
                refresh();
                setLogs();
            });
        }catch (IOException e){
            logger.error(e);
        }
    }

    public void createTourWindow(String resource,String title, boolean update){
        try{
            var gui = createWindow(resource,title);
            if(update) {
                FormController controller = gui.getKey().getController();
                controller.initData(selectedTour);
            }
            gui.getValue().show();
            gui.getValue().setOnHiding(e ->{
                refresh();
                if(!update){
                    select(manager.getLast());
                }
            });
        }catch (IOException e){
            logger.error(e);
        }
    }
}
