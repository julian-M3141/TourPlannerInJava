package at.technikum.gui.viewmodels;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import at.technikum.businessLayer.manager.AppManagerFactory;
import at.technikum.businessLayer.manager.IAppManger;
import at.technikum.models.Tour;

import java.util.HashMap;

public class FormViewModel {
    private final StringProperty tourname = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");

    private final IAppManger manager = AppManagerFactory.getManager();
    //private final TourMap map = new TourMap();

    private Tour editedTour = null;
    private boolean update = false;

    public void initData(Tour tour){
        //erste datensatz gehört ins viewmodel
        editedTour = tour;

        //überschrift und button anpassen
        update = true;
        tourname.set(tour.getName());
        distance.set(String.valueOf(tour.getDistance()));
        from.set(tour.getStart());
        to.set(tour.getFinish());
        description.set(tour.getDescription());
    }


    public void saveData() {
        if(update){
            HashMap<String,String> params = new HashMap<>();
            //maybe update image //also delete old image
            params.put("Tourname",getTourname());
            params.put("Distanz",getDistance());
            params.put("Von",getFrom());
            params.put("Bis",getTo());
            params.put("Beschreibung",getDescription());
            manager.update(editedTour,params); //improve update method later
        }else {
            Tour t = new Tour.Builder()
                    .setId(-1)
                    .setName(tourname.get())
                    .setDescription(description.get())
                    .setDistance(Integer.parseInt(distance.get()))
                    .setStart(from.get())
                    .setFinish(to.get())
                    //.setImage(map.getImage(from.get(),to.get()))
                    .build();
            manager.save(t);
        }
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


    public String getHeader() {
        if(update){
            return "Tour updaten";
        }
        return "Tour erstellen";
    }

    public String getButtonName() {
        if(update){
            return "Updaten";
        }
        return "Erstellen";
    }
    public BooleanBinding isValid(){
        return tourname.isEmpty().or(distance.isEmpty()).or(from.isEmpty()).or(to.isEmpty()).or(description.isEmpty());
    }
}
