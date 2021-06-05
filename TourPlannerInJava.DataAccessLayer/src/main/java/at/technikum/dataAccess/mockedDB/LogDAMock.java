package at.technikum.dataAccess.mockedDB;

import at.technikum.dataAccess.dao.ILogDataAccess;
import at.technikum.models.Log;
import at.technikum.models.Sport;
import at.technikum.models.Tour;
import at.technikum.models.Weather;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class LogDAMock implements ILogDataAccess {
    private LogDAMock(){}
    private static LogDAMock _instance;
    public static LogDAMock Instance(){
        if(_instance == null){
            _instance = new LogDAMock();
        }
        return _instance;
    }
    @Override
    public void update(Log log, Map<String, String> params) {
        if(params.containsKey("Datum")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
            log.setTime(LocalDateTime.parse(params.get("Datum"), formatter));
        }
        if(params.containsKey("Rating")){
            log.setRating(Integer.parseInt(params.get("Rating")));
        }
        if(params.containsKey("Zeit")){
            log.setTimeinminutes(Integer.parseInt(params.get("Zeit")));
        }
        if(params.containsKey("Distanz")){
            log.setDistance(Integer.parseInt(params.get("Distanz")));
        }
        if(params.containsKey("Weather")){
            log.setWeather(Weather.valueOf(params.get("Weather")));
        }
        if(params.containsKey("Weight")){
            log.setWeight(Integer.parseInt(params.get("Weight")));
        }
        if(params.containsKey("Height")){
            log.setHeight(Integer.parseInt(params.get("Height")));
        }
        if(params.containsKey("Sport")){
            log.setSport(Sport.valueOf(params.get("Sport")));
        }
        if(params.containsKey("Steps")){
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

    @Override
    public ArrayList<Log> getLogs(int tourid) {
        var tour = TourDAMock.Instance().get(tourid);
        return tour.map(Tour::getLogs).orElse(null);
    }
}
