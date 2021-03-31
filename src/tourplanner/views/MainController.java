package tourplanner.views;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourlist.setItems(viewModel.getNames());
        tourlist.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                viewModel.select(tourlist.getSelectionModel().getSelectedItem());
            }
        });
        Bindings.bindBidirectional(tourtitle.textProperty(),viewModel.tournameProperty());
        Bindings.bindBidirectional(finish.textProperty(),viewModel.finishProperty());
        Bindings.bindBidirectional(start.textProperty(),viewModel.startProperty());
        Bindings.bindBidirectional(distance.textProperty(),viewModel.distanceProperty());
        Bindings.bindBidirectional(description.textProperty(),viewModel.descriptionProperty());
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        logs.setItems(viewModel.getLogs());
    }

    public void search(ActionEvent actionEvent){ viewModel.search(); }
    public void edit(ActionEvent actionEvent){ viewModel.edit(); }
    public void addLog(ActionEvent actionEvent){ viewModel.addLog();}
    public void deleteLog(ActionEvent actionEvent){ viewModel.deleteLog();}
}
