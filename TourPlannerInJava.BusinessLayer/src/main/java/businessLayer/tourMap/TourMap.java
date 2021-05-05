package businessLayer.tourMap;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TourMap implements ITourMap{
    @Override
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
        } catch (IllegalArgumentException e){
            Logger logger = LogManager.getLogger(TourMap.class);
            logger.error(e);
            return "";
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
        return filename;
        //return Pair<String,Double>(filename,distance);
    }


}
