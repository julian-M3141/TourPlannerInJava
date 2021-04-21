package tourplanner.dataAccess.db;

import tourplanner.dataAccess.dao.ITourDataAccess;
import tourplanner.models.Tour;

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
    private TourDBAccess(){}
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
            throwables.printStackTrace();
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
            throwables.printStackTrace();
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
            throwables.printStackTrace();
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
            if(params.get("Bild")!=null){
                statement.setString(index,params.get("Bild"));
                index++;
            }
            statement.setInt(index,tour.getId());
            statement.execute();
            //select all corresponding logs
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
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
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
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
            throwables.printStackTrace();
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
            throwables.printStackTrace();
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
