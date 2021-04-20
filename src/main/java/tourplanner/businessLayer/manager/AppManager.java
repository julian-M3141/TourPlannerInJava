package tourplanner.businessLayer.manager;

import tourplanner.businessLayer.tourMap.TourMap;
import tourplanner.dataAccess.DataAccess;
import tourplanner.dataAccess.DataAccessFactory;
import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AppManager implements IAppManger {
    private DataAccess dao = DataAccessFactory.getDataAccess();
    @Override
    public List<Tour> getAll() {
        return dao.getAll();
    }

    @Override
    public Optional<Tour> get(int id) {
        return dao.get(id);
    }

    @Override
    public Tour getLast() {
        return dao.getLast();
    }

    @Override
    public void update(Tour tour, HashMap<String, String> params) {
        dao.update(tour,params);
        //maybe later check for new route endpoints and change image and distance
    }

    @Override
    public void save(Tour tour) {
        tour.setImage(new TourMap().getImage(tour.getStart(),tour.getFinish()));
        dao.save(tour);
    }

    @Override
    public void delete(Tour tour) {
        dao.delete(tour);
        //later delete image, maybe extra class for file handling
    }

    @Override
    public ArrayList<Tour> search(String search) {
        return dao.search(search);
    }

    @Override
    public void update(Log log, HashMap<String, String> params) {
        dao.update(log,params);
    }

    @Override
    public void save(Tour tour, Log log) {
        dao.save(tour,log);
    }

    @Override
    public void delete(Tour tour, Log log) {
        dao.delete(tour,log);
        //tour is unnecessary
    }
}
