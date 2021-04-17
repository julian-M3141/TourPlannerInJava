package tourplanner.businessLayer;

import org.json.JSONObject;

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
    public String getImage(String start, String finish){
        String uri = "http://www.mapquestapi.com/directions/v2/route?key=tNhqDcx3ftu1UbNAUQGFsGjTECeXQR4Q&from="+start+"&to="+finish;
        uri = uri.replace(" ","%20");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // cache the result for following requests and return it
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }
        JSONObject route = new JSONObject(response.body()).getJSONObject("route");
        JSONObject boundingBox = route.getJSONObject("boundingBox");
        JSONObject lr = boundingBox.getJSONObject("lr");
        JSONObject ul = boundingBox.getJSONObject("ul");
        String session = route.getString("sessionId");

        uri = "https://www.mapquestapi.com/staticmap/v5/map?key=tNhqDcx3ftu1UbNAUQGFsGjTECeXQR4Q&size=600,400&session="+session+"&boundingBox="+lr.getNumber("lat")+","+lr.getNumber("lng")+","+ul.getNumber("lat")+","+ul.getNumber("lng");

        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<byte[]> imageresponse = null;
        String filename="";
        try {
            imageresponse = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            byte[] imgInBytes = imageresponse.body();
            ByteArrayInputStream bis = new ByteArrayInputStream(imgInBytes);
            BufferedImage bImage2 = ImageIO.read(bis);
            filename = getUniqueFilename();
            ImageIO.write(bImage2, "jpg", new File("pics/"+filename) );
            System.out.println("image created");


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
        return filename;
    }

    String getUniqueFilename(){
        String filename="";
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        do{
            filename = "";
            for(int i=0;i<10;++i){
                filename += alphabet.charAt(r.nextInt(alphabet.length()));
            }
            filename += ".jpg";
        }while (new File(filename).exists());
        return filename;
    }
}
