package at.technikum.businessLayer.tourMap;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TourMapMock implements ITourMap{
    @Override
    public String getImage(String start, String finish) {
        return "testimage.jpg";
    }
}
