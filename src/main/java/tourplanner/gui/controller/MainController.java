package tourplanner.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import tourplanner.gui.viewmodels.MainViewModel;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final MainViewModel viewModel = new MainViewModel();

    private final DoubleProperty scale = new SimpleDoubleProperty(1);
    private final DoubleProperty fontsize = new SimpleDoubleProperty(20);

    //for scaling
    public AnchorPane anchorpane;
    public HBox hbox;
    public HBox hboxtourbar;
    public HBox hboxtourname;
    public HBox hboxlog;
    public SplitPane splitpane;
    public SplitPane splitpanetour;
    public GridPane gridpanedata;
    //end
    public TextField searchbox;
    public ListView<Tour> tourlist;
    public Label tourtitle;
    public Label finish;
    public Label start;
    public Label distance;
    public Label description;
    public TableView<Log> logs;
    public TableColumn<Log,LocalDateTime> date;
    public TableColumn<Log,Integer> time;
    public TableColumn<Log,Integer> rating;
    public TableColumn<Log, Weather> weather;
    public TableColumn<Log, Sport> sport;
    public TableColumn<Log,Integer> steps;
    public TableColumn<Log,Integer> height;
    public TableColumn<Log,Integer> weight;
    public ImageView image;
    public AnchorPane imagepane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //do some scaling
        anchorpane.setStyle("-fx-font-size: "+ 12 * 2);
        hbox.setLayoutY(62);
        AnchorPane.setTopAnchor(splitpane, 120.0);
        tourtitle.setStyle("-fx-font-size: 48");
        hboxtourbar.setMinHeight(hboxtourbar.getPrefHeight()*1.5);
        AnchorPane.setTopAnchor(tourlist,63.);
        hboxtourname.setMinHeight(hboxtourname.getPrefHeight()*1.5);
        gridpanedata.setLayoutY(gridpanedata.getLayoutY()*1.5);
        AnchorPane.setTopAnchor(splitpanetour,AnchorPane.getTopAnchor(splitpanetour)*1.5);
        hboxlog.setMinHeight(hboxlog.getMinHeight()*1.5);
        AnchorPane.setTopAnchor(logs,AnchorPane.getTopAnchor(logs)*1.5);
        TableColumn[] columns = {date,time,rating,weather,sport,steps,weight,height};
        for(var column : columns){
            column.setMinWidth(column.getPrefWidth()*2);
        }
        //end scaling
        //start initializing
        tourlist.setItems(viewModel.getData());
        tourlist.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Tour> call(ListView listView) {
                ListCell<Tour> cell = new ListCell<>() {
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
                return cell;
            }
        });
        tourlist.setOnMouseClicked(mouseEvent -> viewModel.select(tourlist.getSelectionModel().getSelectedItem()));
        Bindings.bindBidirectional(searchbox.textProperty(),viewModel.searchProperty());
        Bindings.bindBidirectional(tourtitle.textProperty(),viewModel.tournameProperty());
        Bindings.bindBidirectional(finish.textProperty(),viewModel.finishProperty());
        Bindings.bindBidirectional(start.textProperty(),viewModel.startProperty());
        Bindings.bindBidirectional(distance.textProperty(),viewModel.distanceProperty());
        Bindings.bindBidirectional(description.textProperty(),viewModel.descriptionProperty());
        Bindings.bindBidirectional(image.imageProperty(),viewModel.imageProperty());
        image.fitHeightProperty().bind(imagepane.heightProperty());
        //format date
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
        rating.setCellFactory(column -> {

            return new TableCell<Log,Integer>() {
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
            };
        });
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        weather.setCellValueFactory(new PropertyValueFactory<>("weather"));
        sport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        height.setCellValueFactory(new PropertyValueFactory<>("height"));
        logs.setItems(viewModel.getLogs());
    }

    public void search(ActionEvent actionEvent){ viewModel.search(); }
    public void addLog(ActionEvent actionEvent){ viewModel.addLog();}
    public void deleteLog(ActionEvent actionEvent){ viewModel.deleteLog(logs.getSelectionModel().getSelectedItem());}
    public void updateLog(ActionEvent actionEvent){ viewModel.updateLog(logs.getSelectionModel().getSelectedItem());}
    public void export(ActionEvent actionEvent){ viewModel.export();}
    public void importFile(ActionEvent actionEvent){
        TextInputDialog dialog = new TextInputDialog(".json");
        dialog.setTitle("Tour importieren");
        dialog.setContentText("Enter the filename:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(viewModel::importFile);
    }
    public void print(ActionEvent actionEvent){ viewModel.print();}
    public void summarize(ActionEvent actionEvent){viewModel.summarizeReport();}
    public void deleteTour(ActionEvent actionEvent){ viewModel.deleteTour(tourlist.getSelectionModel().getSelectedItem());}

    //new tour
    public void newTour(ActionEvent actionEvent){
        viewModel.newTour();
    }

    public void updateTour(ActionEvent actionEvent){
        viewModel.updateTour();
    }
}
