package at.technikum.businessLayer.manager;


import at.technikum.businessLayer.tourMap.TourMapMock;
import at.technikum.dataAccess.mockedDB.DataAccessMockedFactory;
import at.technikum.models.Log;
import at.technikum.models.Sport;
import at.technikum.models.Weather;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import at.technikum.models.Tour;
import org.junit.jupiter.api.TestMethodOrder;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppManagerTest {

    private IAppManger manager = new AppManager(new DataAccessMockedFactory(),new TourMapMock());

//    @BeforeEach
//    public void setup(){
//        manager =
//    }


    @Test
    @Order(3)
    public void testGetAll(){
        List<Tour> tours = manager.getAll();
        assertEquals(2,tours.size());
    }

    @Test
    @Order(4)
    public void testSearch(){
        List<Tour> tours = manager.search("Lange");
        assertEquals(1,tours.size());
        assertEquals("Lange Tour",tours.get(0).getName());
    }

    @Test
    @Order(5)
    public void testSearchResults(){
        List<Tour> tours = manager.search("Tour");
        assertEquals(2,tours.size());
    }

    @Test
    @Order(6)
    public void testUpdateTour(){
        var tour = manager.getLast();
        HashMap<String,String> params = new HashMap<>();
        params.put("Tourname","testname");
        manager.update(tour,params);
        assertEquals("testname",tour.getName());
        //rollback
        params.put("Tourname","Kurze Tour");
        manager.update(tour,params);
    }

    @Test
    @Order(7)
    public void testUpdateTourWithEmptyParameter(){
        var tour = manager.getLast();
        HashMap<String,String> params = new HashMap<>();
        params.put("Tourname","");
        assertThrows(IllegalArgumentException.class,()->manager.update(tour,params));
    }

    @Test
    @Order(1)
    public void testUpdateTourWithoutStartNorEndChanged(){
        var tour = manager.getLast();
        HashMap<String,String> params = new HashMap<>();
        params.put("Bis","London");
        manager.update(tour,params);
        assertEquals("test.jpg",tour.getImage());
    }

    @Test
    @Order(2)
    public void testUpdateTourWithStartOrEndChanged(){
        var tour = manager.getLast();
        HashMap<String,String> params = new HashMap<>();
        params.put("Bis","Wien");
        manager.update(tour,params);
        assertEquals("testimage.jpg",tour.getImage());
        //rollback
        params.put("Bis","London");
        manager.update(tour,params);
    }

    @Test
    @Order(8)
    public void testSave(){
        var tour =  new Tour.Builder()
                .setId(3)
                .setName("Kurze Tour")
                .setDescription("Eine kurze tour von London nach Peking durch flaches Gelände")
                .setStart("Peking")
                .setFinish("London")
                .setDistance(10)
                .setImage("thereisnoimagewiththisfilename.jpg")
                .build();
        manager.save(tour);
        assertEquals(3,manager.getAll().size());
        //rollback
        manager.delete(tour);
    }

    @Test
    @Order(9)
    public void testCreateLog(){
        var log = new Log.Builder()
                .setTime(LocalDateTime.now())
                .setRating(3)
                .setTimeinminutes(20)
                .setDistance(10)
                .setWeather(Weather.Sunny)
                .setWeight(60)
                .setHeight(170)
                .setSport(Sport.Hiking)
                .setSteps(2000)
                .build();
        var tour = manager.getAll().get(0);
        manager.save(tour,log);
        var newtour = manager.get(tour.getId()).get();
        assertEquals(3,newtour.getLogs().size());
        assertEquals(Weather.Sunny,newtour.getLogs().get(2).getWeather());
        manager.delete(newtour,newtour.getLogs().get(2));
    }

    @Test
    @Order(10)
    public void testUpdateLog(){
        HashMap<String,String> params = new HashMap<>();
        params.put("Height","180");
        var log = manager.get(2).get().getLogs().get(0);
        manager.update(log,params);
        log =  manager.get(2).get().getLogs().get(0);
        assertEquals(180,log.getHeight());
    }

    @Test
    @Order(11)
    public void testUpdateLogWithEmptyParameter(){
        HashMap<String,String> params = new HashMap<>();
        params.put("Rating","");
        var log = manager.get(2).get().getLogs().get(0);
        assertThrows(IllegalArgumentException.class,()->manager.update(log,params));
    }

    @Test
    @Order(12)
    public void testUpdateLogWithInvalidDate(){
        HashMap<String,String> params = new HashMap<>();
        params.put("Datum","11/12/1234");
        var log = manager.get(2).get().getLogs().get(0);
        assertThrows(IllegalArgumentException.class,()->manager.update(log,params));
    }

    @Test
    @Order(13)
    public void testUpdateLogDistanceWithNoInteger(){
        HashMap<String,String> params = new HashMap<>();
        params.put("Distanz","abc");
        var log = manager.get(2).get().getLogs().get(0);
        assertThrows(IllegalArgumentException.class,()->manager.update(log,params));
    }

    @Test
    @Order(14)
    public void testUpdateLogTimeWithWrongValue(){
        HashMap<String,String> params = new HashMap<>();
        params.put("Datum","01.01.2000, 24:24");
        var log = manager.get(2).get().getLogs().get(0);
        assertThrows(IllegalArgumentException.class,()->manager.update(log,params));
    }
    @Test
    @Order(15)
    public void testUpdateLogDistanceWithNegativeValue(){
        HashMap<String,String> params = new HashMap<>();
        params.put("Distanz","-1");
        var log = manager.get(2).get().getLogs().get(0);
        assertThrows(IllegalArgumentException.class,()->manager.update(log,params));
    }

    @Test
    @Order(16)
    public void testDeleteLog(){
        var tour = manager.get(2).get();
        manager.delete(tour, tour.getLogs().get(0));
        assertEquals(0,tour.getLogs().size());
    }
}
