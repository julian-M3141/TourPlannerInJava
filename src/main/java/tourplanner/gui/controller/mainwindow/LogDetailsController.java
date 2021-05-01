package tourplanner.gui.controller.mainwindow;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import tourplanner.businessLayer.EnergyConsumption;
import tourplanner.businessLayer.LogData;
import tourplanner.gui.viewmodels.mainwindow.LogDetailsViewModel;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Weather;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LogDetailsController implements Initializable {
    private final LogDetailsViewModel model = new LogDetailsViewModel();
    public LogDetailsViewModel getModel() {
        return model;
    }


    public HBox hboxlog;
    public Button addLog;
    public Button deleteLog;
    public Button updateLog;
    public TableView<Log> logs;
    public TableColumn<Log, LocalDateTime> date;
    public TableColumn<Log,Integer> time;
    public TableColumn<Log,Integer> rating;
    public TableColumn<Log, Weather> weather;
    public TableColumn<Log, Sport> sport;
    public TableColumn<Log,Integer> steps;
    public TableColumn<Log,Double> pace;
    public TableColumn<Log,Double> speed;
    public TableColumn<Log,Double> energy;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hboxlog.setMinHeight(hboxlog.getMinHeight()*1.5);
        AnchorPane.setTopAnchor(logs,AnchorPane.getTopAnchor(logs)*1.5);
        Arrays.asList(date,time,rating,weather,sport,steps,pace,speed,energy).forEach(
                x -> x.setMinWidth(x.getPrefWidth()*2)
        );
        date.setCellFactory(column -> {
            TableCell<Log, LocalDateTime> cell = new TableCell<Log, LocalDateTime>() {
                private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(item.format(format));
                    }
                }
            };

            return cell;
        });
        date.setCellValueFactory(new PropertyValueFactory<>("time"));
        time.setCellFactory(column -> {
            TableCell<Log, Integer> cell = new TableCell<Log,Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        //change format to hh:mm // now: hh:m bei min<10
                        setText(String.format("%d:%02d",item/60,item%60));
                    }
                }
            };

            return cell;
        });
        time.setCellValueFactory(new PropertyValueFactory<>("timeinminutes"));
        rating.setCellFactory(column -> new TableCell<Log,Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText("*".repeat(item));
                }
            }
        });
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        weather.setCellValueFactory(new PropertyValueFactory<>("weather"));
        sport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        pace.setCellValueFactory(cellData -> new SimpleDoubleProperty(LogData.getPace(cellData.getValue())).asObject());
        pace.setCellFactory(column -> new TableCell<>(){
            @Override
            protected void updateItem(Double item,boolean empty){
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(String.format("%.2f min/km",item));
                }
            }
        });
        speed.setCellValueFactory(cellData -> new SimpleDoubleProperty(LogData.getAverageSpeed(cellData.getValue())).asObject());
        speed.setCellFactory(column -> new TableCell<>(){
            @Override
            protected void updateItem(Double item,boolean empty){
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(String.format("%.2f km/h",item));
                }
            }
        });
        energy.setCellValueFactory(cellData -> new SimpleDoubleProperty(EnergyConsumption.getCalories(cellData.getValue())).asObject());
        energy.setCellFactory(column -> new TableCell<>(){
            @Override
            protected void updateItem(Double item,boolean empty){
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(String.format("%.2f kcal",item));
                }
            }
        });
        logs.setItems(model.getLogs());
        deleteLog.disableProperty().bind(logs.getSelectionModel().selectedItemProperty().isNull());
        updateLog.disableProperty().bind(logs.getSelectionModel().selectedItemProperty().isNull());
    }

    public void deleteLog(ActionEvent actionEvent) {
        model.deleteLog(logs.getSelectionModel().getSelectedItem());
    }

    public void addLog(ActionEvent actionEvent) {
        model.addLog();
    }

    public void updateLog(ActionEvent actionEvent) {
        model.updateLog(logs.getSelectionModel().getSelectedItem());
    }
}
