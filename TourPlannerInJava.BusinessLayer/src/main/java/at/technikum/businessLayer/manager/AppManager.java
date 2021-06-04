package at.technikum.businessLayer.manager;

import at.technikum.businessLayer.tourMap.ITourMap;
import at.technikum.businessLayer.tourMap.TourMap;
import org.apache.logging.log4j.LogManager;
import at.technikum.dataAccess.*;
import at.technikum.dataAccess.dao.IDataAccessFactory;
import at.technikum.dataAccess.dao.ILogDataAccess;
import at.technikum.dataAccess.dao.ITourDataAccess;
import at.technikum.models.Log;
import at.technikum.models.Tour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppManager implements IAppManger {

    private final ITourDataAccess tourAccess;
    private final ILogDataAccess logAccess;
    private final ITourMap map;
    private final Logger logger;



//    public AppManager(Status status){
////        IDataAccessFactory factory = (status == Status.DB)? new DBAccessFactory() : new DataAccessMockedFactory();
//        this.status = status;
//        tourAccess = factory.getTourAccess();
//        logAccess = factory.getLogAccess();
//        logger = LogManager.getLogger(AppManager.class);
//        logger.info("starting AppManager");
//    }

    @Autowired
    public AppManager(IDataAccessFactory dataAccessFactory,ITourMap map){
        this.map = map;
        tourAccess = dataAccessFactory.getTourAccess();
        logAccess = dataAccessFactory.getLogAccess();
        logger = LogManager.getLogger(AppManager.class);
        logger.info("starting AppManager");
    }

    @Override
    public List<Tour> getAll() {
        return tourAccess.getAll();
    }

    @Override
    public Optional<Tour> get(int id) {
        return tourAccess.get(id);
    }

    @Override
    public Tour getLast() {
        return tourAccess.getLast();
    }

    @Override
    public void update(Tour tour, HashMap<String, String> params) {
        String start = (params.get("Von")!=null)?params.get("Von"):tour.getStart();
        String finish = (params.get("Bis")!=null)?params.get("Bis"):tour.getFinish();
        //check if tour endpoints changed
        if((!(tour.getStart().equals(start)))||(!(tour.getFinish().equals(finish)))){
            String filename = tour.getImage();
            String newImage = new TourMap().getImage(start, finish);
            if(!newImage.isEmpty()) {
                params.put("Bild", newImage);
            }else{
                logger.error("Route cannot be found");
            }
            try {
                FileHandler.delete("pics/",filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tourAccess.update(tour,params);
        logger.info("Updated tour '"+tour.getName()+"'");
    }

    @Override
    public void save(Tour tour) {
        String filename = map.getImage(tour.getStart(),tour.getFinish());
        tour.setImage(filename);
        tourAccess.save(tour);
        logger.info("Created new tour '"+tour.getName()+"'");
    }

    @Override
    public void delete(Tour tour) {
        String filename = tour.getImage();
        if(!(filename == null) && !(filename.isEmpty())){
            try {
                FileHandler.delete("pics/",filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tourAccess.delete(tour);
        logger.info("Deleted tour '"+tour.getName()+"'");
    }

    @Override
    public ArrayList<Tour> search(String search) {
        logger.info("Searching for '"+search+"'");
        return tourAccess.search(search);
    }

    @Override
    public void update(Log log, HashMap<String, String> params) {
        logger.info("Updated log with ID '"+log.getId()+"'");
        logAccess.update(log,params);
    }

    @Override
    public void save(Tour tour, Log log) {
        logger.info("New log for tour '"+tour.getId()+"'");
        logAccess.save(tour,log);
    }

    @Override
    public void delete(Tour tour, Log log) {
        logger.info("Deleted log with ID '"+log.getId()+"'");
        logAccess.delete(tour,log);
        //tour is unnecessary
    }
}
