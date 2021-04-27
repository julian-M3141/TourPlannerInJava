package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import tourplanner.gui.viewmodels.FormViewModel;
import tourplanner.models.Tour;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class FormController implements Initializable {
    private final FormViewModel viewmodel = new FormViewModel();
    //check if new tour or edit tour
    private boolean update = false;
    private Tour editedTour;
    public TextField tourname;
    public TextField distance;
    public TextField from;
    public TextField to;
    public TextArea description;
    public Button erstelle;


    public void initData(Tour tour){
        //erste datensatz gehört ins viewmodel
        editedTour = tour;

        //überschrift und button anpassen
        update = true;
        tourname.setText(tour.getName());
        distance.setText(String.valueOf(tour.getDistance()));
        from.setText(tour.getStart());
        to.setText(tour.getFinish());
        description.setText(tour.getDescription());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Bindings.bindBidirectional(tourname.textProperty(),viewmodel.tournameProperty());
        Bindings.bindBidirectional(from.textProperty(),viewmodel.fromProperty());
        Bindings.bindBidirectional(to.textProperty(),viewmodel.toProperty());
        Bindings.bindBidirectional(distance.textProperty(),viewmodel.distanceProperty());
        Bindings.bindBidirectional(description.textProperty(),viewmodel.descriptionProperty());
        //check for empty textfields
        erstelle.disableProperty().bind(
                Bindings.createBooleanBinding( () ->
                        tourname.getText().trim().isEmpty(),tourname.textProperty()
                ).or(Bindings.createBooleanBinding( () ->
                        distance.getText().trim().isEmpty(),distance.textProperty()
                ).or(Bindings.createBooleanBinding( () ->
                        from.getText().trim().isEmpty(),from.textProperty()
                ).or(Bindings.createBooleanBinding( () ->
                        to.getText().trim().isEmpty(),to.textProperty()
                ).or(Bindings.createBooleanBinding( () ->
                        description.getText().trim().isEmpty(),description.textProperty()
                )))))
        );


    }

    public void saveData(ActionEvent actionEvent){
        if(update){
            viewmodel.updateTour(editedTour);
        }else{
            viewmodel.saveData();
        }
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


}
