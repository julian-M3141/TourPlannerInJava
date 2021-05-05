package dataAccess.dao;

import models.Tour;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public interface ITourDataAccess {
    public ArrayList<Tour> getAll();
    public Optional<Tour> get(int id);
    public Tour getLast();
    public void update(Tour tour, Map<String,String> params);
    public void save(Tour tour);
    public void delete(Tour tour);
    public ArrayList<Tour> search(String search);
}
