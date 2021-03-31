package tourplanner.dataAccess;

import tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.Optional;

public interface DataAccess<T> {
    public ArrayList<T> getAll();
    public Optional<T> get(int id);
    public void update(T t);
    public void save(T t);
    public void delete(T t);
    public ArrayList<T> search(String search);
}
