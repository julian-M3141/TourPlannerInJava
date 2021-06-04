package at.technikum.businessLayer;

import at.technikum.models.Log;
import at.technikum.models.Sport;

public class EnergyConsumption {
    public static double getCalories(Log log){
        //src for running and bike: https://www.sportunterricht.ch/Theorie/Energie/kalorienverbrauch.php
        if(log.getSport() == Sport.Running){
            return LogData.getAverageSpeed(log)*0.9*log.getWeight()*((double) log.getTimeinminutes()/60);
        }else if (log.getSport() == Sport.Bicycle){
            double calories = 0;
            double speed = LogData.getAverageSpeed(log);
            if(speed < 20){
                calories = speed/2;
            }else if (speed < 35){
                calories = speed/3;
            }else {
                calories = speed/2;
            }
            return calories*log.getWeight()*((double) log.getTimeinminutes()/60);
        }
        //src: https://www.outdoorseiten.net/vb5/forum/outdooraktivit%C3%A4ten/outdoor-basiswissen/kochen-essen/105791-kalorienverbrauch-wandern-die-pandolf-formel
        //assumption: weight bag: 10kg
        // gradient: 20%
        int w = log.getWeight();
        return 0.86*(1.5*w+2*(w+10)*Math.pow((10./w),2)+1.2*(w+10)*(1.5*Math.pow(LogData.getAverageSpeed(log)/3.6,2)+0.35*LogData.getAverageSpeed(log)/3.6*20));
    }
}
