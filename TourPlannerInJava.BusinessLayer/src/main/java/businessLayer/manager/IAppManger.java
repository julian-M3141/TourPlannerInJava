package businessLayer.manager;

import models.Log;
import models.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface IAppManger {
    public List<Tour> getAll();
    public Optional<Tour> get(int id);
    public Tour getLast();
    public void update(Tour tour, HashMap<String,String> params);
    public void save(Tour tour);
    public void delete(Tour tour);
    public ArrayList<Tour> search(String search);
    public void update(Log log, HashMap<String,String> params);
    public void save(Tour tour, Log log);
    public void delete(Tour tour, Log log);
}
