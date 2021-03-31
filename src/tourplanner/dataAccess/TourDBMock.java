package tourplanner.dataAccess;

import tourplanner.models.Log;
import tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class TourDBMock implements DataAccess<Tour>{
    private ArrayList<Tour> tours = new ArrayList<Tour>();

    public TourDBMock(){
        tours.add(new Tour("Lange tour","200km", "London" , "Peking" , "long description ... ", new ArrayList<Log>(
                Arrays.asList(new Log("12.1.2021", "12h", "5 stars"),
                        new Log("18.1.2021", "11h", "4.5 stars")))));

        tours.add(new Tour("Kurze tour","10km", "Peking" , "London" ,"short description", new ArrayList<Log>(
                Arrays.asList(new Log("15.1.2021", "1h5min", "3 stars")))));
    }

    @Override
    public ArrayList<Tour> getAll() {
        return tours;
    }

    @Override
    public Optional<Tour> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Tour tour) {

    }

    @Override
    public void save(Tour tour) {
        tours.add(tour);
    }

    @Override
    public void delete(Tour tour) {
        tours.remove(tour);
    }

    @Override
    public ArrayList<Tour> search(String search) {
        ArrayList<Tour> tmp = new ArrayList<>();
        for(Tour t : tours){
            if(t.getTourname().contains(search)||t.getDescription().contains(search)){
                tmp.add(t);
                continue;
            }
            for(Log l : t.getLogs()){
                if(l.getRating().contains(search)){
                    tmp.add(t);
                    continue;
                }
            }
        }
        return tmp;
    }
}
