package tourplanner.gui.viewmodels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tourplanner.businessLayer.report.BasicTourReport;
import tourplanner.businessLayer.ImportExportTour;
import tourplanner.businessLayer.manager.AppManagerFactory;
import tourplanner.businessLayer.manager.IAppManger;
import tourplanner.businessLayer.report.IReport;
import tourplanner.businessLayer.report.SummarizeReport;
import tourplanner.gui.TourButtonListener;
import tourplanner.gui.controller.FormController;
import tourplanner.gui.controller.LogFormController;
import tourplanner.gui.viewmodels.mainwindow.*;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MainViewModel {

    private final SearchBarViewModel searchBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final LogDetailsViewModel logDetailsViewModel;
    private final MenuBarViewModel menuBarViewModel;

    private final IAppManger manager = AppManagerFactory.getManager();
    private final ImportExportTour handler = new ImportExportTour();
    private final IReport report = new BasicTourReport();
    private final IReport summarizeReport = new SummarizeReport();
    private final TourButtonListener tourButtonListener = new TourButtonListener() {
        @Override
        public void createNewTour() {
            MainViewModel.this.newTour();
        }

        @Override
        public void updateTour() {
            MainViewModel.this.updateTour();
        }

        @Override
        public void deleteTour() {
            MainViewModel.this.deleteTour();
        }
    };

    private final Logger logger;


    //constructor
    public MainViewModel(SearchBarViewModel searchBarViewModel, TourListViewModel tourListViewModel
            , TourDetailsViewModel tourDetailsViewModel, LogDetailsViewModel logDetailsViewModel, MenuBarViewModel menuBarViewModel){
        //get logger
        logger = LogManager.getLogger(MainViewModel.class);
        //assign viewmodel objects
        this.searchBarViewModel = searchBarViewModel;
        this.tourListViewModel = tourListViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.logDetailsViewModel = logDetailsViewModel;
        this.menuBarViewModel = menuBarViewModel;
        //set listeners
        this.searchBarViewModel.addListener(this::search);
        this.tourListViewModel.addTourButtonListener(tourButtonListener);
        this.tourDetailsViewModel.addTourButtonListener(tourButtonListener);
        this.menuBarViewModel.addTourButtonListener(tourButtonListener);
        this.tourListViewModel.addSelectionChangedListener((x,y,selectedTour) -> this.tourDetailsViewModel.setTour(selectedTour));
        this.tourListViewModel.addSelectionChangedListener((x,y,selectedTour) -> {
            if(selectedTour!=null){
                this.logDetailsViewModel.setLogs(selectedTour.getLogs());
            }
        });
        this.menuBarViewModel.setExportFile(this::export);
        this.menuBarViewModel.setImportFile(this::importFile);
        this.menuBarViewModel.setTourReport(this::print);
        this.menuBarViewModel.setSummarizeReport(this::summarizeReport);
        this.menuBarViewModel.setAddLogButton(this::addLog);
        this.logDetailsViewModel.setAddLogButton(this::addLog);
        this.logDetailsViewModel.setUpdateLogButton(this::updateLog);
        this.logDetailsViewModel.setDeleteLogButton(this::deleteLog);
        //set tours
        var data = manager.getAll();
        this.tourListViewModel.setTours(data);
        if(data.size()>0) {
            this.tourDetailsViewModel.setTour(data.get(0));
            this.logDetailsViewModel.setLogs(data.get(0).getLogs());
        }
    }

    public void search(String searchValue){
        tourListViewModel.setTours(manager.search(searchValue));
    }


    public void deleteLog(Log log) {
        manager.delete(tourDetailsViewModel.getSelectedTour(),log );
        refresh();
    }

    public void print(){
        try {
            report.print(tourDetailsViewModel.getSelectedTour());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void export(){
        try {
            handler.export(tourDetailsViewModel.getSelectedTour(),tourDetailsViewModel.getSelectedTour().getName()+".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void importFile(String filename) {
        try {
            manager.save(handler.importTour(filename));
            refresh();
            tourDetailsViewModel.setTour(manager.getLast());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteTour() {
        manager.delete(tourDetailsViewModel.getSelectedTour());
        refresh();
    }

    public void refresh(){
        Tour oldtour = tourDetailsViewModel.getSelectedTour();
        var tourlist = manager.getAll();
        tourListViewModel.setTours(tourlist);
        Optional<Tour> tour = manager.get(oldtour.getId());
        if(tour.isPresent()){
            tourDetailsViewModel.setTour(tour.get());
            logDetailsViewModel.setLogs(tour.get().getLogs());
        }else if(tourlist.size()>0){
            tourDetailsViewModel.setTour(tourlist.get(0));
            logDetailsViewModel.setLogs(tourlist.get(0).getLogs());
        }
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

    public void updateLog(Log log) {
        if(log != null){
            createLogWindow("/views/logForm.fxml","Logeintrag updaten",Optional.of((Log)log));
        }else {
            addLog();
        }
    }

    public void summarizeReport() {
        try {
            summarizeReport.print(tourDetailsViewModel.getSelectedTour());
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
            controller.initTour(tourDetailsViewModel.getSelectedTour());
            log.ifPresent(controller::initData);
            gui.getValue().show();
            gui.getValue().setOnHiding(e -> {
                refresh();
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
                controller.initData(tourDetailsViewModel.getSelectedTour());
            }
            gui.getValue().show();
            gui.getValue().setOnHiding(e ->{
                refresh();
                if(!update){
                    tourDetailsViewModel.setTour(manager.getLast());
                }
            });
        }catch (IOException e){
            logger.error(e);
        }
    }
}
