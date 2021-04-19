package tourplanner.models;

import java.util.HashMap;
import java.util.Map;

public class BoundingBox {
    private Map<String,Double> lr;

    private Map<String,Double> ul;
    private double distance;

    public BoundingBox(Map<String, Double> lr, Map<String, Double> ul, double distance) {
        this.lr = lr;
        this.ul = ul;
        this.distance = distance;
    }

    public Map<String, Double> getLr() {
        return lr;
    }
    public Map<String, Double> getUl() {
        return ul;
    }
    public double getDistance() {
        return distance;
    }

}
