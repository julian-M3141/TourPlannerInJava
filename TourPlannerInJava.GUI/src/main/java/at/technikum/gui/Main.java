package at.technikum.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;

public class Main extends Application {

//    private static AnnotationConfigApplicationContext _context;

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
//        _context = new AnnotationConfigApplicationContext();
//
//        // register all other classes via the IoCContainerConfig via class annotations (see class "implementation")
//        _context.register(GUISpringConfig.class);
//
//        // finish the configuration by (re-)building the context. After that the context is able to instantiate the
//        // registered classes
//        _context.refresh();

        launch(args);
    }
}
