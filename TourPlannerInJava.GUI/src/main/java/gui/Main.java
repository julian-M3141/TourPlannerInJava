package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/MainWindow.fxml")));
        primaryStage.setTitle("TourPlanner");
        primaryStage.setScene(new Scene(root, 1200, 1100));
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(1100);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
