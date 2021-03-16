package sample;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public TableView logs;
    public TableColumn date;
    public TableColumn time;
    public TableColumn rating;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourlist.setItems(viewModel.getNames());
        Bindings.bindBidirectional(tourtitle.textProperty(),viewModel.getTour().tournameProperty());
        Bindings.bindBidirectional(finish.textProperty(),viewModel.getTour().finishProperty());
        Bindings.bindBidirectional(start.textProperty(),viewModel.getTour().startProperty());
        Bindings.bindBidirectional(distance.textProperty(),viewModel.getTour().distanceProperty());
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        logs.setItems(viewModel.getLogs());
    }
}
