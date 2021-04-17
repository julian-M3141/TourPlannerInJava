package tourplanner.dataAccess;

import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class TourDBMock implements DataAccess{
    private ArrayList<Tour> tours = new ArrayList<Tour>();

    public TourDBMock(){
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
    public void update(Tour tour,HashMap<String,String> params) throws NumberFormatException {
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

    @Override
    public void update(Log log, HashMap<String,String> params) {
        if(params.get("Datum")!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
            log.setTime(LocalDateTime.parse(params.get("Datum"), formatter));
        }
        if(params.get("Rating")!=null){
            log.setRating(Integer.parseInt(params.get("Rating")));
        }
        if(params.get("Zeit")!=null){
            log.setTimeinminutes(Integer.parseInt(params.get("Zeit")));
        }
        if(params.get("Distanz")!=null){
            log.setDistance(Integer.parseInt(params.get("Distanz")));
        }
        if(params.get("Weather")!=null){
            log.setWeather(Weather.valueOf(params.get("Weather")));
        }
        if(params.get("Weight")!=null){
            log.setWeight(Integer.parseInt(params.get("Weight")));
        }
        if(params.get("Height")!=null){
            log.setHeight(Integer.parseInt(params.get("Height")));
        }
        if(params.get("Sport")!=null){
            log.setSport(Sport.valueOf(params.get("Sport")));
        }
        if(params.get("Steps")!=null){
            log.setSteps(Integer.parseInt(params.get("Steps")));
        }
    }

    @Override
    public void save(Tour tour, Log log) {
        try{
            log.setId(tour.getLogs().get(tour.getLogs().size()-1).getId()+1);
        }catch(IndexOutOfBoundsException e){
            log.setId(1);
        }
        tour.getLogs().add(log);
    }

    @Override
    public void delete(Tour tour, Log log) {
        tour.getLogs().remove(log);
    }
}
