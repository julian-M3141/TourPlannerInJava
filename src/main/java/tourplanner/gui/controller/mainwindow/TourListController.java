package tourplanner.gui.controller.mainwindow;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import tourplanner.gui.viewmodels.mainwindow.TourListViewModel;
import tourplanner.models.Tour;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListController implements Initializable {

    //private final MainViewModel model = MainViewModel.Instance();

    private final TourListViewModel model = new TourListViewModel();
    public ListView<Tour> tourlist;
    public HBox hboxtourbar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hboxtourbar.setMinHeight(hboxtourbar.getPrefHeight()*1.5);
        AnchorPane.setTopAnchor(tourlist,63.);
        tourlist.setItems(model.getData());
        tourlist.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Tour> call(ListView listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Tour tour, boolean empty) {
                        super.updateItem(tour, empty);
                        if (tour != null && !empty) {
                            setText(tour.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        //tourlist.setOnMouseClicked(mouseEvent -> model.select(tourlist.getSelectionModel().getSelectedItem()));
        tourlist.getSelectionModel().selectedItemProperty().addListener(model.getChangeListener());
    }

    public TourListViewModel getModel() {
        return model;
    }

    public void newTour(ActionEvent actionEvent) {
        model.newTour();
    }

    public void deleteTour(ActionEvent actionEvent) {
        model.deleteTour();
    }

    public void updateTour(ActionEvent actionEvent) {
        model.updateTour();
    }
}
