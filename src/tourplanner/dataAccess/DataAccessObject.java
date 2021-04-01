package tourplanner.dataAccess;

import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DataAccessObject {
    DataAccess dataAccess = null;

    //constructor
    //decide which implementation used
    private DataAccessObject(){
        if (true) {
            dataAccess = new TourDBMock();
        }
    }


    //Singleton
    private static DataAccessObject _dao = null;
    public static DataAccessObject Instance(){
        if (_dao == null){
            _dao = new DataAccessObject();
        }
        return _dao;
    }

    public ArrayList<Tour> getAllTours() {
        return dataAccess.getAll();
    }

    public Optional<Tour> getTour(int id){
        return dataAccess.get(id);
    }

    public void updateTour(Tour tour, HashMap<String,String> params) {
        dataAccess.update(tour,params);
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
    public void update(Log log,HashMap<String,String> params){
        dataAccess.update(log,params);
    }
    public void save(Tour tour,Log log){
        dataAccess.save(tour,log);
    }
    public void delete(Tour tour,Log log){
        dataAccess.delete(tour, log);
    }
}
