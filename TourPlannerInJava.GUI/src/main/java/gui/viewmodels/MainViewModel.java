package gui.viewmodels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import businessLayer.report.BasicTourReport;
import businessLayer.ImportExportTour;
import businessLayer.manager.AppManagerFactory;
import businessLayer.manager.IAppManger;
import businessLayer.report.IReport;
import businessLayer.report.SummarizeReport;
import gui.TourButtonListener;
import gui.controller.FormController;
import gui.controller.LogFormController;
import gui.viewmodels.mainwindow.*;
import models.Log;
import models.Tour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MainViewModel {

    private final SearchBarViewModel searchBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final LogDetailsViewModel logDetailsViewModel;
    private final MenuBarViewModel menuBarViewModel;
    private final Pane root;
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
    public MainViewModel(Pane root, SearchBarViewModel searchBarViewModel, TourListViewModel tourListViewModel
            , TourDetailsViewModel tourDetailsViewModel, LogDetailsViewModel logDetailsViewModel, MenuBarViewModel menuBarViewModel){
        //get logger
        logger = LogManager.getLogger(MainViewModel.class);
        this.root = root;
        //assign viewmodel objects
        this.searchBarViewModel = searchBarViewModel;
        this.tourListViewModel = tourListViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.logDetailsViewModel = logDetailsViewModel;
        this.menuBarViewModel = menuBarViewModel;

        //set listeners
        setUpSearchBarAndTourDetails();
        setUpTourList();
        setUpLogDetails();
        setUpMenuBar();

        //setup window data by filling lists
        setUpWindowData();
    }
    private void setUpSearchBarAndTourDetails(){
        this.searchBarViewModel.addListener(this::search);
        this.tourDetailsViewModel.addTourButtonListener(tourButtonListener);
    }
    private void setUpTourList(){
        tourListViewModel.addTourButtonListener(tourButtonListener);
        tourListViewModel.addSelectionChangedListener((x,y,selectedTour) -> tourDetailsViewModel.setTour(selectedTour));
        tourListViewModel.addSelectionChangedListener((x,y,selectedTour) -> {
            if(selectedTour!=null){
                logDetailsViewModel.setLogs(selectedTour.getLogs());
            }
        });
    }
    private void setUpLogDetails(){
        this.logDetailsViewModel.setAddLogButton(this::addLog);
        this.logDetailsViewModel.setUpdateLogButton(this::updateLog);
        this.logDetailsViewModel.setDeleteLogButton(this::deleteLog);
    }
    private void setUpMenuBar(){
        this.menuBarViewModel.addTourButtonListener(tourButtonListener);
        this.menuBarViewModel.setExportFile(this::export);
        this.menuBarViewModel.setImportFile(this::importFile);
        this.menuBarViewModel.setTourReport(this::print);
        this.menuBarViewModel.setSummarizeReport(this::summarizeReport);
        this.menuBarViewModel.setAddLogButton(this::addLog);
    }
    private void setUpWindowData(){
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

    public void print(){
        try {
            report.print(tourDetailsViewModel.getSelectedTour());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void summarizeReport() {
        try {
            summarizeReport.print(tourDetailsViewModel.getSelectedTour());
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
    }

    public void export(){
        try {
            handler.export(tourDetailsViewModel.getSelectedTour(),tourDetailsViewModel.getSelectedTour().getName()+".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void importFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a tour");
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if(file!=null) {
            try {
                manager.save(handler.importTour(file.getAbsolutePath()));
                refresh();
                tourDetailsViewModel.setTour(manager.getLast());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
    public void deleteTour() {
        manager.delete(tourDetailsViewModel.getSelectedTour());
        refresh();
    }

    public void addLog() {
        createLogWindow("/views/logForm.fxml","Neuen Logeintrag erstellen",Optional.empty());
    }
    public void updateLog(Log log) {
        if(log != null){
            createLogWindow("/views/logForm.fxml","Logeintrag updaten",Optional.of(log));
        }else {
            addLog();
        }
    }
    public void deleteLog(Log log) {
        manager.delete(tourDetailsViewModel.getSelectedTour(),log );
        refresh();
    }

    private Pair<FXMLLoader,Stage> createWindow(String resource,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(loader.load(), 1200, 800));
        return new Pair<>(loader,stage);
    }
    private void createLogWindow(String resource, String title, Optional<Log> log){
        try{
            var gui = createWindow(resource,title);
            LogFormController controller = gui.getKey().getController();
            controller.initTour(tourDetailsViewModel.getSelectedTour());
            log.ifPresent(controller::initData);
            gui.getValue().show();
            root.setDisable(true);
            gui.getValue().setOnHiding(e -> {
                refresh();
                root.setDisable(false);
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
            root.setDisable(true);
            gui.getValue().setOnHiding(e ->{
                refresh();
                if(!update){
                    tourDetailsViewModel.setTour(manager.getLast());
                }
                root.setDisable(false);
            });
        }catch (IOException e){
            logger.error(e);
        }
    }
}
