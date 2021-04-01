package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import tourplanner.gui.viewmodels.MainViewModel;
import tourplanner.models.Tour;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final MainViewModel viewModel = new MainViewModel();

    public TextField searchbox;
    public ListView tourlist;
    public Label tourtitle;
    public Label finish;
    public Label start;
    public Label distance;
    public Label description;
    public TableView logs;
    public TableColumn date;
    public TableColumn time;
    public TableColumn rating;
    public ImageView image;
    public AnchorPane imagepane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourlist.setItems(viewModel.getData());
        tourlist.setCellFactory(new Callback<ListView<Tour>, ListCell<Tour>>() {
            @Override
            public ListCell call(ListView listView) {
                ListCell<Tour> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(Tour tour,boolean empty){
                        super.updateItem(tour,empty);
                        if(tour!=null && !empty){
                            setText(tour.getTourname());
                        }else{
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        tourlist.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                viewModel.select(tourlist.getSelectionModel().getSelectedItem());
            }
        });
        Bindings.bindBidirectional(searchbox.textProperty(),viewModel.searchProperty());
        Bindings.bindBidirectional(tourtitle.textProperty(),viewModel.tournameProperty());
        Bindings.bindBidirectional(finish.textProperty(),viewModel.finishProperty());
        Bindings.bindBidirectional(start.textProperty(),viewModel.startProperty());
        Bindings.bindBidirectional(distance.textProperty(),viewModel.distanceProperty());
        Bindings.bindBidirectional(description.textProperty(),viewModel.descriptionProperty());
        Bindings.bindBidirectional(image.imageProperty(),viewModel.imageProperty());
        image.fitHeightProperty().bind(imagepane.heightProperty());
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        logs.setItems(viewModel.getLogs());
    }

    public void search(ActionEvent actionEvent){ viewModel.search(); }
    public void edit(ActionEvent actionEvent){ viewModel.edit(); }
    public void addLog(ActionEvent actionEvent){ viewModel.addLog();}
    public void deleteLog(ActionEvent actionEvent){ viewModel.deleteLog(logs.getSelectionModel().getSelectedItem());}
    public void updateLog(ActionEvent actionEvent){ viewModel.updateLog(logs.getSelectionModel().getSelectedItem());}
    public void export(ActionEvent actionEvent){ viewModel.export();}
    public void print(ActionEvent actionEvent){ viewModel.print();}
    public void deleteTour(ActionEvent actionEvent){ viewModel.deleteTour(tourlist.getSelectionModel().getSelectedItem());}

    //new tour
    public void newTour(ActionEvent actionEvent){
        viewModel.newTour();
    }

    public void updateTour(ActionEvent actionEvent){
        viewModel.updateTour();
    }
}
