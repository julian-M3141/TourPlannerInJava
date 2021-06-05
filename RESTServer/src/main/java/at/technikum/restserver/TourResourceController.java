package at.technikum.restserver;

import at.technikum.businessLayer.manager.IAppManger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import at.technikum.models.Log;
import at.technikum.models.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;



@RestController
@RequestMapping("/resources/tours")
public class TourResourceController {

    @Autowired
    IAppManger manager;

    @Autowired
    private ObjectMapper om;

    @GetMapping("/{id}")
    public Tour retrieve(@PathVariable int id){
        return manager.get(id).get();
    }

    @GetMapping
    public List<Tour> retrieveAll(@RequestParam(defaultValue = "")String search){
        return (search.isEmpty())? manager.getAll() : manager.search(search);
    }

    @GetMapping("/{tourid}/logs")
    public List<Log> retireveLog(@PathVariable int tourid){
        return manager.get(tourid).get().getLogs();
    }

    @GetMapping("/{tourid}/logs/{logid}")
    public Log retireveLog(@PathVariable int tourid,@PathVariable int logid){
        var tour = manager.get(tourid);
        if(tour.isPresent()){
            var logs = tour.get().getLogs();
            for(var log : logs){
                if(log.getId() == logid){
                    return log;
                }
            }
        }
        return null;
    }

    @PostMapping
    public Tour saveTour(@RequestBody String jsonString) throws JsonProcessingException {
        var tour = om.readValue(jsonString,Tour.class);
        manager.save(tour);
        tour = manager.getLast();
        return tour;
    }

    @PostMapping("/{tourid}/logs")
    public String saveLog(@PathVariable int tourid, @RequestBody String jsonString) throws JsonProcessingException {
        var tour = manager.get(tourid);
        if(tour.isPresent()){
            var log = om.readValue(jsonString,Log.class);
            manager.save(tour.get(),log);
            return "save successful";
        }
        return "save not successful";
    }

    @PutMapping("/{tourid}")
    public String updateTour(@PathVariable int tourid,@RequestBody String jsonString) throws JsonProcessingException {
        var tour = manager.get(tourid);
        if(tour.isPresent()){
            var map = om.readValue(
                    jsonString,
                    new TypeReference<HashMap<String,String>>() {}
            );
            manager.update(tour.get(),map);
            return "update successful";
        }
        return "not so successful";
    }

    @PutMapping("/{tourid}/logs/{logid}")
    public String updateTour(@PathVariable int tourid,@PathVariable int logid,@RequestBody String jsonString) throws JsonProcessingException {
        var tour = manager.get(tourid);
        if(tour.isPresent()){
            for(var log : tour.get().getLogs()){
                if(log.getId()==logid){
                    var map = om.readValue(
                            jsonString,
                            new TypeReference<HashMap<String,String>>() {}
                    );
                    manager.update(log,map);
                    return "update successful";
                }
            }
        }
        return "not so successful";
    }

    @DeleteMapping("/{tourid}")
    public String updateTour(@PathVariable int tourid){
        var tour = manager.get(tourid);
        if(tour.isPresent()){
            manager.delete(tour.get());
            return "delete successful";
        }
        return "not so successful";
    }

    @DeleteMapping("/{tourid}/logs/{logid}")
    public String updateTour(@PathVariable int tourid,@PathVariable int logid){
        var tour = manager.get(tourid);
        if(tour.isPresent()){
            for(var log : tour.get().getLogs()){
                if(log.getId()==logid){
                    manager.delete(tour.get(),log);
                    return "delete successful";
                }
            }
        }
        return "not so successful";
    }
}
