package tourplanner.businessLayer.manager;

import tourplanner.businessLayer.tourMap.TourMap;
import tourplanner.dataAccess.*;
import tourplanner.dataAccess.dao.IDataAccessFactory;
import tourplanner.dataAccess.dao.ILogDataAccess;
import tourplanner.dataAccess.dao.ITourDataAccess;
import tourplanner.dataAccess.db.DBAccessFactory;
import tourplanner.dataAccess.mockedDB.DataAccessMockedFactory;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AppManager implements IAppManger {
    Status status;
    private ITourDataAccess tourAccess;
    private ILogDataAccess logAccess;
    public AppManager(Status status){
        IDataAccessFactory factory = (status == Status.DB)? new DBAccessFactory() : new DataAccessMockedFactory();
        this.status = status;
        tourAccess = factory.getTourAccess();
        logAccess = factory.getLogAccess();
    }

    @Override
    public List<Tour> getAll() {
        return tourAccess.getAll();
    }

    @Override
    public Optional<Tour> get(int id) {
        return tourAccess.get(id);
    }

    @Override
    public Tour getLast() {
        return tourAccess.getLast();
    }

    @Override
    public void update(Tour tour, HashMap<String, String> params) {
        tourAccess.update(tour,params);
        //maybe later check for new route endpoints and change image and distance
    }

    @Override
    public void save(Tour tour) {
        String filename = (status == Status.DB) ? new TourMap().getImage(tour.getStart(),tour.getFinish()) : "";
        tour.setImage(filename);
        tourAccess.save(tour);
    }

    @Override
    public void delete(Tour tour) {
        tourAccess.delete(tour);
        //later delete image, maybe extra class for file handling
    }

    @Override
    public ArrayList<Tour> search(String search) {
        return tourAccess.search(search);
    }

    @Override
    public void update(Log log, HashMap<String, String> params) {
        logAccess.update(log,params);
    }

    @Override
    public void save(Tour tour, Log log) {
        logAccess.save(tour,log);
    }

    @Override
    public void delete(Tour tour, Log log) {
        logAccess.delete(tour,log);
        //tour is unnecessary
    }
}
