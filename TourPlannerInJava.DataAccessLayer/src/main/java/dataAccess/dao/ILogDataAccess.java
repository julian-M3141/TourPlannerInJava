package dataAccess.dao;

import models.Log;
import models.Tour;

import java.util.ArrayList;
import java.util.Map;

public interface ILogDataAccess {
    public void update(Log log, Map<String,String> params);
    public void save(Tour tour, Log log);
    public void delete(Tour tour, Log log);
    public ArrayList<Log> getLogs(int tourid);

}
