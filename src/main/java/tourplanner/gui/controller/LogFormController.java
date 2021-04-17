package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tourplanner.gui.viewmodels.LogFormViewModel;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.net.URL;
import java.util.ResourceBundle;

public class LogFormController implements Initializable {
    private final LogFormViewModel viewmodel = new LogFormViewModel();

    private boolean update = false;
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
        update = true;
        sport.getSelectionModel().select(viewmodel.getSport());
        rating.getSelectionModel().select(viewmodel.getRating());
        weather.getSelectionModel().select(viewmodel.getWeather());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weather.setItems(viewmodel.getWeatherlist());
        sport.setItems(viewmodel.getSportslist());
        rating.setItems(viewmodel.getRatinglist());
        rating.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                viewmodel.ratingProperty().set(String.valueOf(t1.intValue()+1));
            }
        });
        weather.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                viewmodel.weatherProperty().set(viewmodel.getWeatherlist().get(t1.intValue()));
            }
        });
        sport.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                viewmodel.sportProperty().set(viewmodel.getSportslist().get(t1.intValue()));
            }
        });

        Bindings.bindBidirectional(date.valueProperty(),viewmodel.dateProperty());
        Bindings.bindBidirectional(hours.textProperty(),viewmodel.hoursProperty());
        Bindings.bindBidirectional(minutes.textProperty(),viewmodel.minutesProperty());
        Bindings.bindBidirectional(time.textProperty(),viewmodel.timeProperty());
        Bindings.bindBidirectional(distance.textProperty(), viewmodel.distanceProperty());
        Bindings.bindBidirectional(steps.textProperty(),viewmodel.stepsProperty());
        Bindings.bindBidirectional(weight.textProperty(),viewmodel.weightProperty());
        Bindings.bindBidirectional(height.textProperty(),viewmodel.heightProperty());
        //check for empty textfields
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(date.valueProperty(),
                        hours.textProperty(),
                        minutes.textProperty(),
                        time.textProperty(),
                        rating.valueProperty(),
                        distance.textProperty(),
                        weather.valueProperty(),
                        sport.valueProperty(),
                        steps.textProperty(),
                        weight.textProperty(),
                        height.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return (date.getValue()==null ||
                        hours.getText().isEmpty() ||
                        minutes.getText().isEmpty() ||
                        time.getText().isEmpty() ||
                        rating.getValue() == null ||
                        distance.getText().isEmpty() ||
                        weather.getValue() == null ||
                        sport.getValue() == null ||
                        steps.getText().isEmpty() ||
                        weight.getText().isEmpty() ||
                        height.getText().isEmpty());
            }
        };
        button.disableProperty().bind(bb);
        //check for numbers
        checkForInt(minutes,0,59);
        checkForInt(hours,0,23);
        checkForInt(time);
        checkForInt(distance);
        checkForInt(steps);
        checkForInt(weight);
        checkForInt(height);
    }

    //Check input text field for numbers
    private void checkForInt(TextField textfield){
        textfield.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                try{
                    if(Integer.parseInt(textfield.getText())<0){
                        textfield.setText("");
                    }
                }catch (NumberFormatException e){
                    textfield.setText("");
                }
            }

        });
    }
    private void checkForInt(TextField textfield,int from,int to){
        textfield.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                try{
                    int val = Integer.parseInt(textfield.getText());
                    if(val < from || val > to){
                        textfield.setText("");
                    }
                }catch (NumberFormatException e){
                    textfield.setText("");
                }
            }

        });
    }
    public void saveData(ActionEvent actionEvent){
        if(update){
            viewmodel.updateLog();
        }else{
            viewmodel.saveData();
        }
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

}
