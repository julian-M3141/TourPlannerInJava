package tourplanner.businessLayer.tourMap;

import org.json.JSONObject;
import tourplanner.dataAccess.ConfigurationManager;
import tourplanner.models.BoundingBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class TourMap {
    public String getImage(String start, String finish) {
        MapQuestHandler handler = new MapQuestHandler();
        Double distance = null;
        String filename;
        try {
            var box = handler.createRouteRequest(start,finish);
            distance = box.getDistance();
            var image = handler.createStaticMapRequest(box);
            filename = FileNameGenerator.getUniqueFilename("jpg","pics/");
            ImageIO.write(image, "jpg", new File("pics/"+filename) );
            System.out.println("image created");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
        return filename;
    }


}
