package gui.controller.mainwindow;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import gui.viewmodels.mainwindow.TourDetailsViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class TourDetailsController implements Initializable {
    //    private final MainViewModel model = MainViewModel.Instance();

    @FXML
    private LogDetailsController logDetailsController;

    public LogDetailsController getLogDetailsController() {
        return logDetailsController;
    }



    private final TourDetailsViewModel model = new TourDetailsViewModel();
    public HBox hboxtourname;

    public Label tourtitle;
    public GridPane gridpanedata;
    public Label start;
    public Label finish;
    public Label distance;
    public SplitPane splitpanetour;
    public Label description;
    public AnchorPane imagepane;
    public ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourtitle.setStyle("-fx-font-size: 48");
        hboxtourname.setMinHeight(hboxtourname.getPrefHeight()*1.5);
        gridpanedata.setLayoutY(gridpanedata.getLayoutY()*1.5);
        AnchorPane.setTopAnchor(splitpanetour,AnchorPane.getTopAnchor(splitpanetour)*1.5);

        Bindings.bindBidirectional(tourtitle.textProperty(),model.tournameProperty());
        Bindings.bindBidirectional(finish.textProperty(),model.finishProperty());
        Bindings.bindBidirectional(start.textProperty(),model.startProperty());
        Bindings.bindBidirectional(distance.textProperty(),model.distanceProperty());
        Bindings.bindBidirectional(description.textProperty(),model.descriptionProperty());
        Bindings.bindBidirectional(image.imageProperty(),model.imageProperty());
        image.fitHeightProperty().bind(imagepane.heightProperty());
        //logs

    }



    public TourDetailsViewModel getModel() {
        return model;
    }

    public void updateTour(ActionEvent actionEvent) {
        model.updateTour();
    }

    public void deleteTour(ActionEvent actionEvent) {
        model.deleteTour();
    }


}
