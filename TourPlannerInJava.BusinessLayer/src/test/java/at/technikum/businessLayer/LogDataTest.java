package at.technikum.businessLayer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import at.technikum.models.Log;
import at.technikum.models.Sport;
import at.technikum.models.Weather;
import java.time.LocalDateTime;

public class LogDataTest {
    Log log;
    @BeforeEach
    public void setup(){
        log = new Log.Builder()
                .setId(1)
                .setTime(LocalDateTime.now())
                .setRating(5)
                .setTimeinminutes(60)
                .setDistance(10)
                .setWeather(Weather.Cloudy)
                .setWeight(80)
                .setHeight(180)
                .setSport(Sport.Running)
                .setSteps(10000)
                .build();
    }

    @Test
    public void testPace(){
        double pace = LogData.getPace(log);
        Assertions.assertEquals(60./10,pace);
    }

    @Test
    public void testAverageSpeed(){
        double speed = LogData.getAverageSpeed(log);
        Assertions.assertEquals(10.,speed);
    }

    @Test
    public void testEnergyConsumptionRunning(){
        log.setSport(Sport.Running);
        double cal = EnergyConsumption.getCalories(log);
        //exp ~ 720
        Assertions.assertEquals(9.*80,cal);
    }

    @Test
    public void testEnergyConsumptionBicycle(){
        log.setSport(Sport.Bicycle);
        log.setTimeinminutes(30);
        double cal = EnergyConsumption.getCalories(log);
        //exp ~ 266
        Assertions.assertEquals(20./3*80*0.5,cal);
    }
    @Test
    public void testEnergyConsumptionHiking(){
        log.setSport(Sport.Hiking);
        log.setTimeinminutes(120);
        double cal = EnergyConsumption.getCalories(log);
        double expected = 0.86*(120+180*0.125*0.125+1.2*90*(1.5*5*5/3.6/3.6+0.35*5/3.6*20));
        //exp ~ 1277
        Assertions.assertEquals(expected,cal);
    }
}
