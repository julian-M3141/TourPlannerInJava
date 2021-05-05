package dataAccess.db;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dataAccess.dao.ILogDataAccess;
import dataAccess.dao.ITourDataAccess;
import models.Tour;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class TourDBAccess implements ITourDataAccess {
    private static TourDBAccess _instance;
    private Logger logger;

    private TourDBAccess(){
        logger = LogManager.getLogger(TourDBAccess.class);
    }
    public static TourDBAccess getInstance(){
        if(_instance == null){
            _instance = new TourDBAccess();
        }
        return _instance;
    }

    @Override
    public ArrayList<Tour> getAll() {
        ArrayList<Tour> list= new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                select * from tour;""");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                list.add(createTour(result));
            }
            //select all corresponding logs
            return list;
        } catch (SQLException | IOException throwables) {
            logger.error(throwables);
        }
        return null;
    }

    @Override
    public Optional<Tour> get(int id) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                    select * from tour where tourid = ?;""");
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return Optional.ofNullable(createTour(result));
            }
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            logger.error(throwables);
        }
        return Optional.empty();
    }

    @Override
    public Tour getLast() {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                    select * from tour where tourid = (select max(tourid) from tour);""");
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return createTour(result);
            }
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            logger.error(throwables);
        }
        return null;
    }

    @Override
    public void update(Tour tour, Map<String, String> params) {
        try {
            StringBuilder state = new StringBuilder("update tour set ");
            String[][] paramlist = {{"Tourname","name"},{"Beschreibung","description"},{"Von","start"}
                                ,{"Bis","finish"},{"Distanz","distance"},{"Bild","image"}};
            Arrays.asList(paramlist).forEach(x -> {
                if(params.get(x[0])!=null){
                    state.append(x[1] + " = ?, ");
                }
            });
            state.deleteCharAt(state.length()-2);
            state.append(" where tourid = ?;");
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(state.toString());
            Pair[] param = {new Pair<>("Tourname",String.class),new Pair<>("Beschreibung",String.class)
                    ,new Pair<>("Von",String.class),new Pair<>("Bis",String.class)
                    ,new Pair<>("Distanz",int.class),new Pair<>("Bild",String.class)};
            var wrapper = new Object(){ int index = 1; };
            Arrays.asList(param).forEach(x ->{
                if(params.get(x.getKey())!=null){
                    try {
                        if(x.getValue() == String.class) {
                            statement.setString(wrapper.index, params.get(x.getKey()));
                        }else if(x.getValue() == int.class){
                            statement.setInt(wrapper.index,Integer.parseInt(params.get(x.getKey())));
                        }
                        wrapper.index++;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            statement.setInt(wrapper.index,tour.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            logger.error(throwables);
        }
    }

    @Override
    public void save(Tour tour) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
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

            if(tour.getLogs()!=null) {
                //newtour as id from tour is -1
                Tour newTour = getLast();
                ILogDataAccess logDataAccess = LogDBAccess.getInstance();
                for (var log : tour.getLogs()) {
                    logDataAccess.save(newTour, log);
                }
            }
        } catch (SQLException | IOException throwables) {
            logger.error(throwables);
        }
    }

    @Override
    public void delete(Tour tour) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                    delete from tour where tourid = ?;""");
            statement.setInt(1,tour.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            logger.error(throwables);
        }
    }

    @Override
    public ArrayList<Tour> search(String search) {
        ArrayList<Tour> list= new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("""
                    select * from tour where lower(name) like ? or lower(description) like ?;""");
            statement.setString(1,"%"+search.toLowerCase()+"%");
            statement.setString(2,"%"+search.toLowerCase()+"%");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                list.add(createTour(result));
            }
            //select all corresponding logs
            return list;
        } catch (SQLException | IOException throwables) {
            logger.error(throwables);
        }
        return null;
    }

    private Tour createTour(ResultSet result) throws SQLException {
        return new Tour.Builder()
                .setId(result.getInt("tourid"))
                .setName(result.getString("name"))
                .setDescription(result.getString("description"))
                .setStart(result.getString("start"))
                .setFinish(result.getString("finish"))
                .setDistance(result.getInt("distance"))
                .setImage(result.getString("image"))
                .setLogs(LogDBAccess.getInstance().getLogs(result.getInt("tourid")))
                .build();
    }
}
