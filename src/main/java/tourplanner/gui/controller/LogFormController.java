package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import tourplanner.gui.viewmodels.LogFormViewModel;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LogFormController implements Initializable {
    private final LogFormViewModel viewmodel = new LogFormViewModel();
    public GridPane gridPane;

    public void initTour(Tour tour){
        viewmodel.setTour(tour);
    }
    public DatePicker date;
    public TextField hours;
    public TextField minutes;
    public TextField time;
    public ChoiceBox<String> rating;
    public TextField distance;
    public ChoiceBox<String> weather;
    public ChoiceBox<String> sport;
    public TextField steps;
    public TextField weight;
    public TextField height;
    public Button button;


    public void initData(Log log){
        //erste datensatz gehört ins viewmodel
        //set choiceboxes later
        //überschrift und button anpassen
        viewmodel.setLog(log);
        sport.getSelectionModel().select(viewmodel.getSport());
        rating.getSelectionModel().select(viewmodel.getRating());
        weather.getSelectionModel().select(viewmodel.getWeather());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scaleScene(2);
        setUpSelects();
        createBindings();
        addChangeListeners();
    }

    private void scaleScene(double scale){
        gridPane.setStyle("-fx-font-size: "+12*scale);
        gridPane.getRowConstraints().forEach(x->x.setMinHeight(x.getPrefHeight()*scale));
        gridPane.getColumnConstraints().get(0).setMinWidth(gridPane.getColumnConstraints().get(0).getMinWidth()*scale);
        hours.setMinWidth(hours.getMinWidth()*scale);
        minutes.setMinWidth(minutes.getMinWidth()*scale);
    }
    private void setUpSelects(){
        weather.setItems(viewmodel.getWeatherlist());
        sport.setItems(viewmodel.getSportslist());
        rating.setItems(viewmodel.getRatinglist());
    }

    private void createBindings(){
        //bindings/changelisteners on selects
        rating.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> viewmodel.ratingProperty().set(String.valueOf(t1.intValue()+1)));
        weather.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> viewmodel.weatherProperty().set(viewmodel.getWeatherlist().get(t1.intValue())));
        sport.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> viewmodel.sportProperty().set(viewmodel.getSportslist().get(t1.intValue())));

        //bindings on textfields
        Bindings.bindBidirectional(date.valueProperty(),viewmodel.dateProperty());
        Bindings.bindBidirectional(hours.textProperty(),viewmodel.hoursProperty());
        Bindings.bindBidirectional(minutes.textProperty(),viewmodel.minutesProperty());
        Bindings.bindBidirectional(time.textProperty(),viewmodel.timeProperty());
        Bindings.bindBidirectional(distance.textProperty(), viewmodel.distanceProperty());
        Bindings.bindBidirectional(steps.textProperty(),viewmodel.stepsProperty());
        Bindings.bindBidirectional(weight.textProperty(),viewmodel.weightProperty());
        Bindings.bindBidirectional(height.textProperty(),viewmodel.heightProperty());
    }

    private void addChangeListeners(){
        Arrays.asList(time,distance,steps,weight,height).forEach(viewmodel::addIntegerListener);
        viewmodel.addIntegerRangeListener(hours,0,23);
        viewmodel.addIntegerRangeListener(minutes,0,59);
        //check for empty textfields
        button.disableProperty().bind(viewmodel.isEmpty());
    }

    public void saveData(ActionEvent actionEvent){
        viewmodel.saveData();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

}
