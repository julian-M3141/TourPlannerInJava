package tourplanner.dataAccess.mockedDB;

import tourplanner.dataAccess.dao.ILogDataAccess;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.lang.reflect.Method;
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

    @Override
    public ArrayList<Log> getLogs(int tourid) {
        var tour = TourDAMock.Instance().get(tourid);
        return tour.map(Tour::getLogs).orElse(null);
    }
}
