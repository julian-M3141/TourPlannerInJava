package tourplanner.dataAccess;

import tourplanner.models.Log;
import tourplanner.models.Sport;
import tourplanner.models.Tour;
import tourplanner.models.Weather;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DataAccessDataBase implements DataAccess{

    private Connection _connection = null;

    public void openConnection(String url, String user, String password) throws SQLException {
        _connection = DriverManager.getConnection(url, user, password);
    }
    public void closeConnection() throws SQLException {
        _connection.close();
        _connection = null;
    }

    public DataAccessDataBase() throws SQLException {
        openConnection("jdbc:postgresql://localhost:5432/postgres","tourplanner","geheimesTourPlannerPasswort");
    }

    @Override
    public ArrayList<Tour> getAll() {
        ArrayList<Tour> list= new ArrayList<>();
        try {
            PreparedStatement statement = _connection.prepareStatement("""
                    select * from tour;""");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                list.add(new Tour.Builder()
                        .setId(result.getInt("tourid"))
                        .setName(result.getString("name"))
                        .setDescription(result.getString("description"))
                        .setStart(result.getString("start"))
                        .setFinish(result.getString("finish"))
                        .setDistance(result.getInt("distance"))
                        .setImage(result.getString("image"))
                        .setLogs(getLogs(result.getInt("tourid")))
                        .build());
            }
            //select all corresponding logs
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Tour> get(int id) {
        try {
            PreparedStatement statement = _connection.prepareStatement("""
                    select * from tour where tourid = ?;""");
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return Optional.ofNullable(new Tour.Builder()
                        .setId(result.getInt("tourid"))
                        .setName(result.getString("name"))
                        .setDescription(result.getString("description"))
                        .setStart(result.getString("start"))
                        .setFinish(result.getString("finish"))
                        .setDistance(result.getInt("distance"))
                        .setImage(result.getString("image"))
                        .setLogs(getLogs(result.getInt("tourid")))
                        .build());
            }
            //select all corresponding logs
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Tour getLast() {
        try {
            PreparedStatement statement = _connection.prepareStatement("""
                    select * from tour where tourid = (select max(tourid) from tour);""");
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new Tour.Builder()
                        .setId(result.getInt("tourid"))
                        .setName(result.getString("name"))
                        .setDescription(result.getString("description"))
                        .setStart(result.getString("start"))
                        .setFinish(result.getString("finish"))
                        .setDistance(result.getInt("distance"))
                        .setImage(result.getString("image"))
                        .setLogs(getLogs(result.getInt("tourid")))
                        .build();
            }
            //select all corresponding logs
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Tour tour, HashMap<String, String> params) {
        try {
            StringBuilder state = new StringBuilder("update tour set ");
            if(params.get("Tourname")!=null){
                state.append("name = ?, ");
            }
            if(params.get("Beschreibung")!=null){
                state.append("description = ?, ");
            }
            if(params.get("Von")!=null){
                state.append("start = ?, ");
            }
            if(params.get("Bis")!=null){
                state.append("finish = ?, ");
            }
            if(params.get("Distanz")!=null){
                state.append("distance = ?, ");
            }
            state.deleteCharAt(state.length()-2);
            state.append(" where tourid = ?;");
            PreparedStatement statement = _connection.prepareStatement(state.toString());
            int index = 1;
            if(params.get("Tourname")!=null){
                statement.setString(index,params.get("Tourname"));
                index++;
            }
            if(params.get("Beschreibung")!=null){
                statement.setString(index,params.get("Beschreibung"));
                index++;
            }
            if(params.get("Von")!=null){
                statement.setString(index,params.get("Von"));
                index++;
            }
            if(params.get("Bis")!=null){
                statement.setString(index,params.get("Bis"));
                index++;
            }
            if(params.get("Distanz")!=null){
                statement.setInt(index,Integer.parseInt(params.get("Distanz")));
                index++;
            }
            statement.setInt(index,tour.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Tour tour) {
        try {
            PreparedStatement statement = _connection.prepareStatement("""
                    insert into tour (name,description,start,finish,distance,image)
                    values (?,?,?,?,?,?);""");
            statement.setString(1,tour.getName());
            statement.setString(2,tour.getDescription());
            statement.setString(3,tour.getStart());
            statement.setString(4,tour.getFinish());
            statement.setInt(5,tour.getDistance());
            statement.setString(6,tour.getImage());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Tour tour) {
        try {
            PreparedStatement statement = _connection.prepareStatement("""
                    delete from tour where tourid = ?;""");
            statement.setInt(1,tour.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<Tour> search(String search) {
        ArrayList<Tour> list= new ArrayList<>();
        try {
            PreparedStatement statement = _connection.prepareStatement("""
                    select * from tour where lower(name) like ? or lower(description) like ?;""");
            statement.setString(1,"%"+search.toLowerCase()+"%");
            statement.setString(2,"%"+search.toLowerCase()+"%");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                list.add(new Tour.Builder()
                        .setId(result.getInt("tourid"))
                        .setName(result.getString("name"))
                        .setDescription(result.getString("description"))
                        .setStart(result.getString("start"))
                        .setFinish(result.getString("finish"))
                        .setDistance(result.getInt("distance"))
                        .setImage(result.getString("image"))
                        .setLogs(getLogs(result.getInt("tourid")))
                        .build());
            }
            //select all corresponding logs
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Log log, HashMap<String, String> params) {
        try {
            StringBuilder state = new StringBuilder("update logs set ");
            if(params.get("Datum")!=null){
                state.append("time = ?, ");
            }
            if(params.get("Rating")!=null){
                state.append("rating = ?, ");
            }
            if(params.get("Zeit")!=null){
                state.append("timeinminutes = ?, ");
            }
            if(params.get("Distanz")!=null){
                state.append("distance = ?, ");
            }
            if(params.get("Weather")!=null){
                state.append("weather = ?, ");
            }
            if(params.get("Weight")!=null){
                state.append("weight = ?, ");
            }
            if(params.get("Height")!=null){
                state.append("height = ?, ");
            }
            if(params.get("Sport")!=null){
                state.append("sport = ?, ");
            }
            if(params.get("Steps")!=null){
                state.append("schrittanzahl = ?, ");
            }
            state.deleteCharAt(state.length()-2);
            state.append(" where logid = ?;");
            PreparedStatement statement = _connection.prepareStatement(state.toString());
            int index = 1;
            if(params.get("Datum")!=null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
                statement.setTimestamp(index,Timestamp.valueOf(LocalDateTime.parse(params.get("Datum"), formatter)));
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Tour tour, Log log) {
        try {
            PreparedStatement statement = _connection.prepareStatement("""
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Tour tour, Log log) {
        try {
            PreparedStatement statement = _connection.prepareStatement("""
                    delete from logs where logid = ?;""");
            statement.setInt(1,log.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Log> getLogs(int tourid){
        ArrayList<Log> list= new ArrayList<>();
        try {
            PreparedStatement statement = _connection.prepareStatement("""
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
