package at.technikum.dataAccess.mockedDB;

import at.technikum.dataAccess.dao.ITourDataAccess;
import at.technikum.models.Log;
import at.technikum.models.Sport;
import at.technikum.models.Tour;
import at.technikum.models.Weather;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class TourDAMock implements ITourDataAccess {
    private final ArrayList<Tour> tours = new ArrayList<Tour>();
    private static TourDAMock _instance;
    public static TourDAMock Instance(){
        if(_instance == null){
            _instance = new TourDAMock();
        }
        return _instance;
    }
    private TourDAMock(){
        //create new tours
        tours.add(new Tour.Builder()
                .setId(1)
                .setName("Lange Tour")
                .setDescription("Eine lange tour von London nach Peking durch steiles Gelände")
                .setStart("London")
                .setFinish("Peking")
                .setDistance(200)
                .setImage("test.jpg")
                .setLogs(new ArrayList<Log>(Arrays.asList(new Log.Builder()
                                .setId(1)
                                .setTime(LocalDateTime.now())
                                .setRating(4)
                                .setTimeinminutes(400)
                                .setDistance(200)
                                .setWeather(Weather.Sunny)
                                .setWeight(80)
                                .setHeight(180)
                                .setSport(Sport.Bicycle)
                                .setSteps(800000)
                                .build(),
                        new Log.Builder()
                                .setId(2)
                                .setTime(LocalDateTime.now())
                                .setRating(5)
                                .setTimeinminutes(300)
                                .setDistance(201)
                                .setWeather(Weather.Rain)
                                .setWeight(88)
                                .setHeight(182)
                                .setSport(Sport.Running)
                                .setSteps(800000)
                                .build())))
                .build());
        tours.add(new Tour.Builder()
                .setId(2)
                .setName("Kurze Tour")
                .setDescription("Eine kurze tour von London nach Peking durch flaches Gelände")
                .setStart("Peking")
                .setFinish("London")
                .setDistance(10)
                .setImage("test.jpg")
                .setLogs(new ArrayList<Log>(Arrays.asList(new Log.Builder()
                        .setId(3)
                        .setTime(LocalDateTime.now())
                        .setRating(3)
                        .setTimeinminutes(20)
                        .setDistance(10)
                        .setWeather(Weather.Foggy)
                        .setWeight(60)
                        .setHeight(170)
                        .setSport(Sport.Hiking)
                        .setSteps(2000)
                        .build())))
                .build());
    }
    @Override
    public ArrayList<Tour> getAll() {
        return tours;
    }

    @Override
    public Optional<Tour> get(int id) {
        Tour t = null;
        for(Tour tour : tours){
            if(tour.getId() == id){
                t = tour;
                break;
            }
        }
        return Optional.ofNullable(t);
    }

    @Override
    public Tour getLast() {
        return tours.get(tours.size()-1);
    }

    @Override
    public void update(Tour tour, Map<String, String> params) {
        if(params.get("Tourname")!=null){
            tour.setName(params.get("Tourname"));
        }
        if(params.get("Beschreibung")!=null){
            tour.setDescription(params.get("Beschreibung"));
        }
        if(params.get("Von")!=null){
            tour.setStart(params.get("Von"));
        }
        if(params.get("Bis")!=null){
            tour.setFinish(params.get("Bis"));
        }
        if(params.get("Distanz")!=null){
            tour.setDistance(Integer.parseInt(params.get("Distanz")));
        }
    }

    @Override
    public void save(Tour tour) {
        tour.setId(getLast().getId()+1);
        tours.add(tour);
    }

    @Override
    public void delete(Tour tour) {
        tours.remove(tour);
    }

    @Override
    public ArrayList<Tour> search(String search) {
        if(search==null || search.isEmpty()){
            return tours;
        }
        ArrayList<Tour> tmp = new ArrayList<>();
        for(Tour t : tours){
            if(t.getName().contains(search)||t.getDescription().contains(search)||t.getStart().contains(search)||t.getFinish().contains(search)){
                tmp.add(t);
                continue;
            }
            for(Log l : t.getLogs()){
                try {
                    if (l.getRating() == Integer.parseInt(search)) {
                        tmp.add(t);
                        break;
                    }
                }catch (NumberFormatException e){}
            }
        }
        return tmp;
    }
}
