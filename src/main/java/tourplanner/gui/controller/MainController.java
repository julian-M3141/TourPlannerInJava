package tourplanner.gui.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import tourplanner.gui.controller.mainwindow.*;
import tourplanner.gui.viewmodels.MainViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable  {

    @FXML
    private SearchBarController searchBarController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private TourListController tourListController;

    @FXML
    private TourDetailsController tourDetailsController;


    private MainViewModel viewModel;

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
        viewModel = new MainViewModel(searchBarController.getModel(),tourListController.getModel(),tourDetailsController.getModel()
                , tourDetailsController.getLogDetailsController().getModel(), menuBarController.getModel());
        //do some scaling
        anchorpane.setStyle("-fx-font-size: "+ 12 * 2);
        searchBar.setLayoutY(58);
        AnchorPane.setTopAnchor(splitpane, 120.0);
    }
}
