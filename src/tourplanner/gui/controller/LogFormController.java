package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    public TextField date;
    public TextField time;
    public TextField rating;
    public Button button;

    public void initData(Log log){
        //erste datensatz gehört ins viewmodel

        //überschrift und button anpassen
        viewmodel.setLog(log);
        update = true;
        date.setText(log.getDate());
        time.setText(log.getTime());
        rating.setText(log.getRating());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bindings.bindBidirectional(date.textProperty(),viewmodel.dateProperty());
        Bindings.bindBidirectional(time.textProperty(),viewmodel.timeProperty());
        Bindings.bindBidirectional(rating.textProperty(),viewmodel.ratingProperty());
        //check for empty textfields
        button.disableProperty().bind(
                Bindings.createBooleanBinding( () ->
                        date.getText().trim().isEmpty(),date.textProperty()
                ).or(Bindings.createBooleanBinding( () ->
                        time.getText().trim().isEmpty(),time.textProperty()
                ).or(Bindings.createBooleanBinding( () ->
                        rating.getText().trim().isEmpty(),rating.textProperty()
                )))
        );
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
