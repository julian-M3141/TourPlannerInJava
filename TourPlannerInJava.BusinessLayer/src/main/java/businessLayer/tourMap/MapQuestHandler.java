package businessLayer.tourMap;

import javafx.util.Pair;
import org.json.JSONObject;
import dataAccess.ConfigurationManager;
import models.BoundingBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.AbstractMap;
import java.util.Map;

public class MapQuestHandler {
    private String session = null;
    private String key = null;
    private Pair<HttpClient,HttpRequest> createHttpRequest(String uri){
        //replace spaces with http equivalent
        uri = uri.replace(" ","%20");
        //create http request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        return new Pair<>(client,request);
    }
    public BoundingBox createRouteRequest(String from, String to) throws IOException, InterruptedException, IllegalArgumentException {
        //get mapquest key from config file
        key = ConfigurationManager.GetConfigPropertyValue("MapQuestKey");
        //build uri
        var http = createHttpRequest("http://www.mapquestapi.com/directions/v2/route?key="+key+"&from="+from+"&to="+to);
        //send request
        HttpResponse<String> response = http.getKey().send(http.getValue(), HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        if(new JSONObject(response.body()).getJSONObject("info").getInt("statuscode")!= 0){
            throw new IllegalArgumentException("cannot find route");
        }
        //transform into json object
        JSONObject route = new JSONObject(response.body()).getJSONObject("route");
        JSONObject boundingBox = route.getJSONObject("boundingBox");
        //get lr and ul boces
        JSONObject lr = boundingBox.getJSONObject("lr");
        JSONObject ul = boundingBox.getJSONObject("ul");
        //get distance
        double distance = route.getDouble("distance");
        //store session id for static map request
        session = route.getString("sessionId");
        return new BoundingBox(Map.ofEntries(
                new AbstractMap.SimpleEntry<>("lat",lr.getDouble("lat")),
                new AbstractMap.SimpleEntry<>("lng",lr.getDouble("lng"))
            ), Map.ofEntries(
                new AbstractMap.SimpleEntry<>("lat",ul.getDouble("lat")),
                new AbstractMap.SimpleEntry<>("lng",ul.getDouble("lng"))
            ),distance);
    }

    public BufferedImage createStaticMapRequest(BoundingBox box) throws IOException, InterruptedException {
        if(session == null){
            throw new NullPointerException("createRouteRequest has to be called before createStaticMapRequest");
        }
        key = ConfigurationManager.GetConfigPropertyValue("MapQuestKey");
        var http = createHttpRequest("https://www.mapquestapi.com/staticmap/v5/map?key="
                +key+"&size=1200,800&session="+session+"&boundingBox="
                +box.getLr().get("lat")+","
                +box.getLr().get("lng")+","
                +box.getUl().get("lat")+","
                +box.getUl().get("lng")
        );

        HttpResponse<byte[]> imageresponse = http.getKey().send(http.getValue(), HttpResponse.BodyHandlers.ofByteArray());
        byte[] imgInBytes = imageresponse.body();
        ByteArrayInputStream bis = new ByteArrayInputStream(imgInBytes);
        return ImageIO.read(bis);
        //return bImage2;
    }
}
