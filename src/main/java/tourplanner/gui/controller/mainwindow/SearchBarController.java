package tourplanner.gui.controller.mainwindow;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tourplanner.gui.viewmodels.mainwindow.SearchBarViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarController implements Initializable {
//    private final MainViewModel model = MainViewModel.Instance();

    private final SearchBarViewModel model = new SearchBarViewModel();

    public TextField searchbox;
    public Button searchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bindings.bindBidirectional(searchbox.textProperty(),model.searchProperty());
        searchButton.disableProperty().bind(searchbox.textProperty().isEmpty());
    }


    public SearchBarViewModel getModel() {
        return model;
    }

    public void search(){ model.search(); }

    public void clearSearch() {
        model.clearSearch();
    }
}
