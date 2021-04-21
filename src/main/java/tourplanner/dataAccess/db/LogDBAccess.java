package tourplanner.dataAccess.db;

import tourplanner.dataAccess.dao.ILogDataAccess;
import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LogDBAccess implements ILogDataAccess {
    private static LogDBAccess _instance;
    private LogDBAccess(){}
    public static LogDBAccess getInstance(){
        if(_instance == null){
            _instance = new LogDBAccess();
        }
        return _instance;
    }
    @Override
    public void update(Log log, Map<String, String> params) {
        try {
            StringBuilder state = new StringBuilder("update logs set ");
            String[][] paramlist = {{"Datum","time"},{"Rating","rating"},{"Zeit","timeinminutes"},{"Distanz","distance"}
                    ,{"Weather","weather"},{"Weight","weight"},{"Height","height"},{"Sport","sport"},{"Steps","schrittanzahl"}};
            Arrays.asList(paramlist).forEach( x ->{
                if(params.get(x[0])!=null){
                    state.append(x[1]+" = ?, ");
                }
            });
            state.deleteCharAt(state.length()-2);
            state.append(" where logid = ?;");
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(state.toString());
            int index = 1;
            if(params.get("Datum")!=null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
                statement.setTimestamp(index, Timestamp.valueOf(LocalDateTime.parse(params.get("Datum"), formatter)));
                index++;
            }
            if(params.get("Rating")!=null){
                statement.setInt(index,Integer.parseInt(params.get("Rating")));
                index++;
            }
            if(params.get("Zeit")!=null){
                statement.setInt(index,Integer.parseInt(params.get("Zeit")));
                index++;
            }
            if(params.get("Distanz")!=null){
                statement.setInt(index,Integer.parseInt(params.get("Distanz")));
                index++;
            }
            if(params.get("Weather")!=null){
                statement.setString(index,params.get("Weather"));
                index++;
            }
            if(params.get("Weight")!=null){
                statement.setInt(index,Integer.parseInt(params.get("Weight")));
                index++;
            }
            if(params.get("Height")!=null){
                statement.setInt(index,Integer.parseInt(params.get("Height")));
                index++;
            }
            if(params.get("Sport")!=null){
                statement.setString(index,params.get("Sport"));
                index++;
            }
            if(params.get("Steps")!=null){
                statement.setInt(index,Integer.parseInt(params.get("Steps")));
                index++;
            }
            statement.setInt(index,log.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Tour tour, Log log) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                    insert into logs (tourid,time,rating,timeinminutes,distance,weather,weight,height,sport,schrittanzahl)
                     values (?,?,?,?,?,?,?,?,?,?);""");
            statement.setInt(1,tour.getId());
            statement.setTimestamp(2,Timestamp.valueOf(log.getTime()));
            statement.setInt(3,log.getRating());
            statement.setInt(4,log.getTimeinminutes());
            statement.setInt(5,log.getDistance());
            statement.setString(6,log.getWeather().toString());
            statement.setInt(7,log.getWeight());
            statement.setInt(8,log.getHeight());
            statement.setString(9,log.getSport().toString());
            statement.setInt(10,log.getSteps());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Tour tour, Log log) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                    delete from logs where logid = ?;""");
            statement.setInt(1,log.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<Log> getLogs(int tourid) {
        ArrayList<Log> list= new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                    select * from logs where tourid = ?;""");
            statement.setInt(1,tourid);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                list.add(new Log.Builder()
                        .setId(result.getInt("logid"))
                        .setTime(result.getTimestamp("time").toLocalDateTime())
                        .setRating(result.getInt("rating"))
                        .setTimeinminutes(result.getInt("timeinminutes"))
                        .setDistance(result.getInt("distance"))
                        .setWeather(Weather.valueOf(result.getString("weather")))
                        .setWeight(result.getInt("weight"))
                        .setHeight(result.getInt("height"))
                        .setSport(Sport.valueOf(result.getString("sport")))
                        .setSteps(result.getInt("schrittanzahl"))
                        .build());
            }
            //select all corresponding logs
            return list;
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
