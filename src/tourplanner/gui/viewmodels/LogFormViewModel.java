package tourplanner.gui.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tourplanner.dataAccess.DataAccessObject;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.HashMap;

public class LogFormViewModel {
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty time = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");

    private DataAccessObject dao = DataAccessObject.Instance();
    private Tour editedTour = null;
    public void setTour(Tour tour){
        editedTour = tour;
    }
    private Log log = null;

    public void saveData(){
        dao.save(editedTour,new Log(getDate(),getTime(),getRating()));
    }

    public void updateLog(){
        HashMap<String,String> params = new HashMap<>();
        params.put("Datum",getDate());
        params.put("Zeit",getTime());
        params.put("Rating",getRating());
        if(log != null){
            dao.update(log,params);
        }
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getRating() {
        return rating.get();
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public void setLog(Log log) {
        this.log = log;
    }
}
