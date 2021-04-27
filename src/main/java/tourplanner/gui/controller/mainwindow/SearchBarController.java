package tourplanner.gui.controller.mainwindow;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import tourplanner.gui.viewmodels.MainViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarController implements Initializable {
    private final MainViewModel model = MainViewModel.Instance();

    public TextField searchbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bindings.bindBidirectional(searchbox.textProperty(),model.searchProperty());
    }


    public void search(ActionEvent actionEvent){ model.search(); }
}
