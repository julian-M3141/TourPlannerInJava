package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TourPlanner");
        primaryStage.setScene(new Scene(root, 450, 550));
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(550);
        primaryStage.show();
    }


    public static void main(String[] args) {
        TourReport report = new TourReport();
        try {
            report.print(new Tour("Lange tour","200km", "London" , "Peking" , "long description ... ", new ArrayList<Log>(){{
                add(new Log("12.1.2021", "12h", "5 stars"));
                add(new Log("18.1.2021", "11h", "4.5 stars"));
            }}));
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
