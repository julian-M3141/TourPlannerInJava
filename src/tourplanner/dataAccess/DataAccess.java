package tourplanner.dataAccess;

import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public interface DataAccess {
    public ArrayList<Tour> getAll();
    public Optional<Tour> get(int id);
    public void update(Tour tour, HashMap<String,String> params);
    public void save(Tour tour);
    public void delete(Tour tour);
    public ArrayList<Tour> search(String search);
    public void update(Log log, HashMap<String,String> params);
    public void save(Tour tour, Log log);
    public void delete(Tour tour, Log log);
}
