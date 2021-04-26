package tourplanner.businessLayer;

import tourplanner.models.Log;

public class LogData {
    public static double getPace(Log log){
        return ((double)log.getTimeinminutes())/log.getDistance();
    }
    public static double getAverageSpeed(Log log){
        return ((double)log.getDistance()/((double) log.getTimeinminutes()/60));
    }
}
