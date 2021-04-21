package tourplanner.businessLayer.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tourplanner.dataAccess.Status;
import tourplanner.models.Tour;

import java.util.List;

public class AppManagerTest {
    AppManager manager;
    @BeforeEach
    public void setup(){
        manager =  new AppManager(Status.TEST);
    }


    @Test
    public void testSearch(){
        List<Tour> tours = manager.search("Lange");
        Assertions.assertEquals("Lange Tour",tours.get(0).getName());
    }
    @Test
    public void testSearchResults(){
        List<Tour> tours = manager.search("Tour");
        Assertions.assertEquals(2,tours.size());
    }
}
