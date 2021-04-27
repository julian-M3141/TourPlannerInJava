package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import tourplanner.gui.viewmodels.MainViewModel;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final MainViewModel viewModel = MainViewModel.Instance();

    private final DoubleProperty scale = new SimpleDoubleProperty(1);
    private final DoubleProperty fontsize = new SimpleDoubleProperty(20);

    //for scaling
    public AnchorPane anchorpane;
    public SplitPane splitpane;
    //end
    //includes
    public HBox searchBar;
    public MenuBar menuBar;
    public AnchorPane tourList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //do some scaling
        anchorpane.setStyle("-fx-font-size: "+ 12 * 2);
        searchBar.setLayoutY(58);
        AnchorPane.setTopAnchor(splitpane, 120.0);
    }
}
