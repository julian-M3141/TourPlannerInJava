package at.technikum.gui.viewmodels.mainwindow;

import at.technikum.gui.viewmodels.MainViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import at.technikum.gui.TourButtonListener;
import at.technikum.models.Tour;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TourDetailsViewModel {
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

    private final StringProperty tourname = new SimpleStringProperty("");
    private final StringProperty finish = new SimpleStringProperty("");
    private final StringProperty start = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private Tour selectedTour;
    private final Logger logger = LogManager.getLogger(TourDetailsViewModel.class);
    private final List<TourButtonListener> tourButtonListeners = new ArrayList<>();
    public void addTourButtonListener(TourButtonListener tourButtonListener){
        tourButtonListeners.add(tourButtonListener);
    }
    public void setTour(Tour tour){
        if(tour!=null) {
            selectedTour = tour;
            setTourData();
        }
    }
    public Tour getSelectedTour(){
        return selectedTour;
    }

    private void setTourData(){
        tourname.set(selectedTour.getName());
        finish.set(selectedTour.getFinish());
        start.set(selectedTour.getStart());
        distance.set(selectedTour.getDistance() + " km");
        description.set(selectedTour.getDescription());
        try {
            image.set(new Image(new FileInputStream("pics/"+selectedTour.getImage())));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            logger.error("No image available for tour '"+tourname.get() +"', set default image");
            try {
                image.set(new Image(Objects.requireNonNull(MainViewModel.class.getClassLoader().getResourceAsStream("pics/test.jpeg"))));
            }catch (NullPointerException n){
                logger.fatal("Cannot find default image");
            }
        }
    }

    public void updateTour() {
        for(var listener : tourButtonListeners){
            listener.updateTour();
        }
    }

    public void deleteTour() {
        for(var listener : tourButtonListeners){
            listener.deleteTour();
        }
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public String getTourname() {
        return tourname.get();
    }

    public StringProperty tournameProperty() {
        return tourname;
    }

    public String getFinish() {
        return finish.get();
    }

    public StringProperty finishProperty() {
        return finish;
    }

    public String getStart() {
        return start.get();
    }

    public StringProperty startProperty() {
        return start;
    }

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}
