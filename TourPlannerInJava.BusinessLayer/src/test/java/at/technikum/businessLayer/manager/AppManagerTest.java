package at.technikum.businessLayer.manager;


import at.technikum.businessLayer.tourMap.TourMapMock;
import at.technikum.dataAccess.mockedDB.DataAccessMockedFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import at.technikum.models.Tour;
import org.springframework.test.context.ContextConfiguration;


import java.util.List;

@ContextConfiguration
public class AppManagerTest {

    private IAppManger manager;

    @BeforeEach
    public void setup(){
        manager = new AppManager(new DataAccessMockedFactory(),new TourMapMock());
    }

    @Test
    public void testSearch(){
        List<Tour> tours = manager.search("Lange");
        Assertions.assertEquals(1,tours.size());
        Assertions.assertEquals("Lange Tour",tours.get(0).getName());
    }

    @Test
    public void testSearchResults(){
        List<Tour> tours = manager.search("Tour");
        Assertions.assertEquals(2,tours.size());
    }
}
