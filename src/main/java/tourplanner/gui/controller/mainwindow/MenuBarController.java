package tourplanner.gui.controller.mainwindow;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import tourplanner.gui.viewmodels.MainViewModel;
import tourplanner.gui.viewmodels.mainwindow.MenuBarViewModel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {
    //private final MainViewModel model = MainViewModel.Instance();

    private final MenuBarViewModel model = new MenuBarViewModel();
    public MenuBarViewModel getModel() {
        return model;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void importFile(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog(".json");
        dialog.setTitle("Tour importieren");
        dialog.setContentText("Enter the filename:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(model::importFile);
    }

    public void export(ActionEvent actionEvent) {
        model.export();
    }

    public void print(ActionEvent actionEvent) {
        model.print();
    }

    public void summarize(ActionEvent actionEvent) {
        model.summarizeReport();
    }

    public void newTour(ActionEvent actionEvent) {
        model.newTour();
    }

    public void updateTour(ActionEvent actionEvent) {
        model.updateTour();
    }

    public void deleteTour(ActionEvent actionEvent) {
        model.deleteTour();
    }

    public void addLog(ActionEvent actionEvent) {
        model.addLog();
    }
}
