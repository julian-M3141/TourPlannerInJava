package tourplanner.dataAccess;

import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DataAccessFactory {
    private static DataAccess dataAccess;
    public static DataAccess getDataAccess(){
        if (dataAccess == null) {
            if (true) {
                try {
                    dataAccess = new DataAccessDataBase();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    dataAccess = new TourDBMock();
                }
            }else{
                dataAccess = new TourDBMock();
            }
        }
        return dataAccess;
    }

    //constructor
    //decide which implementation used
    private DataAccessFactory(){
        if (true) {
            try {
                dataAccess = new DataAccessDataBase();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                dataAccess = new TourDBMock();
            }
        }else{
            dataAccess = new TourDBMock();
        }
    }


    //Singleton
    private static DataAccessFactory _dao = null;
    public static DataAccessFactory Instance(){
        if (_dao == null){
            _dao = new DataAccessFactory();
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
    public Tour getLast(){
        return dataAccess.getLast();
    }
}
