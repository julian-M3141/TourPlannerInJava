package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import tourplanner.gui.viewmodels.FormViewModel;
import tourplanner.models.Tour;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class FormController implements Initializable {
    private final FormViewModel viewmodel = new FormViewModel();
    //check if new tour or edit tour
    public Label header;
    public TextField tourname;
    public TextField distance;
    public TextField from;
    public TextField to;
    public TextArea description;
    public Button erstelle;

    @FXML
    public GridPane gridPane;


    // is used to init tour for updating a tour
    public void initData(Tour tour){
        viewmodel.initData(tour);
        erstelle.setText(viewmodel.getButtonName());
        header.setText(viewmodel.getHeader());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        gridPane.setStyle("-fx-font-size: "+12*2);
        gridPane.getRowConstraints().forEach(x -> x.setMinHeight(x.getPrefHeight()*1.5));
        Bindings.bindBidirectional(tourname.textProperty(),viewmodel.tournameProperty());
        Bindings.bindBidirectional(from.textProperty(),viewmodel.fromProperty());
        Bindings.bindBidirectional(to.textProperty(),viewmodel.toProperty());
        Bindings.bindBidirectional(distance.textProperty(),viewmodel.distanceProperty());
        Bindings.bindBidirectional(description.textProperty(),viewmodel.descriptionProperty());
        erstelle.disableProperty().bind(viewmodel.isValid());
    }

    public void saveData(ActionEvent actionEvent){
        viewmodel.saveData();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


}
