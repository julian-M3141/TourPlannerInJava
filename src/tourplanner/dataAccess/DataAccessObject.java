package tourplanner.dataAccess;

import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.Optional;

public class DataAccessObject {
    DataAccess<Tour> dataAccess = null;
    DataAccess<Log> dataAccess1 = null;

    public DataAccessObject(){
        if (true) {
            dataAccess = new TourDBMock();
        }
    }

    public ArrayList<Tour> getAllTours() {
        return dataAccess.getAll();
    }

    public Optional<Tour> getTour(int id){
        return dataAccess.get(id);
    }

    public void updateTour(Tour tour) {
        dataAccess.update(tour);
    }

    public void saveTour(Tour tour) {
        dataAccess.save(tour);
    }

    public void deleteTour(Tour tour) {
        dataAccess.delete(tour);
    }

    public ArrayList<Tour> search(String search) {
        return dataAccess.search(search);
    }
}
