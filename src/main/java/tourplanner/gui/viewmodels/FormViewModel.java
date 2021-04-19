package tourplanner.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tourplanner.businessLayer.tourMap.TourMap;
import tourplanner.dataAccess.DataAccessFactory;
import tourplanner.models.Tour;

import java.util.HashMap;

public class FormViewModel {
    private final StringProperty tourname = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");

    private final DataAccessFactory dao = DataAccessFactory.Instance();
    private final TourMap map = new TourMap();


    public void saveData() {
        Tour t = new Tour.Builder()
                .setId(-1)
                .setName(tourname.get())
                .setDescription(description.get())
                .setDistance(Integer.parseInt(distance.get()))
                .setStart(from.get())
                .setFinish(to.get())
                .setImage(map.getImage(from.get(),to.get()))
                .build();
        dao.saveTour(t);
    }

    public void updateTour(Tour tour){
        //update method will later be edited
        HashMap<String,String> params = new HashMap<>();
        //maybe update image //also delete old image
        params.put("Tourname",getTourname());
        params.put("Distanz",getDistance());
        params.put("Von",getFrom());
        params.put("Bis",getTo());
        params.put("Beschreibung",getDescription());
        dao.updateTour(tour,params); //improve update method later
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public String getTo() {
        return to.get();
    }

    public StringProperty toProperty() {
        return to;
    }

    public String getFrom() {
        return from.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public String getTourname() {
        return tourname.get();
    }

    public StringProperty tournameProperty() {
        return tourname;
    }
}
