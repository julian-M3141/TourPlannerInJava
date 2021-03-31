package tourplanner.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller implements Initializable {
    private final MainViewModel viewModel = new MainViewModel();

    @FXML
    public TableView tableView;
    public TableColumn tournameColumn;
    public TableColumn distanzColumn;
    public TextField tournameTextfield;
    public TextField distanzTextfield;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tournameColumn.setCellValueFactory(new PropertyValueFactory<>("tourname"));
        distanzColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        tableView.setItems(viewModel.getData());

        tournameTextfield.textProperty().bindBidirectional(viewModel.currentTournameProperty());
        distanzTextfield.textProperty().bindBidirectional(viewModel.currentDistanceProperty());

    }

    @FXML
    public void saveAction(ActionEvent actionEvent) {
        viewModel.saveData();
    }
}
