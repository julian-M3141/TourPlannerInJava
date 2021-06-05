package at.technikum.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@AllArgsConstructor
public class Log {
    //private member variables
    private int id;
    private LocalDateTime time;
    //maybe enum later
    private int rating;
    private int timeinminutes;
    private int distance;
    private Weather weather;
    private int weight;
    private int height;
    private Sport sport;
    private int steps;

    public Log() {
        super();
    }

    //Builder class
    public static class Builder{
        private int id=-1;
        private LocalDateTime time=null;
        //maybe enum later
        private int rating=-1;
        private int timeinminutes=-1;
        private int distance=-1;
        private Weather weather=null;
        private int weight=-1;
        private int height=-1;
        private Sport sport=null;
        private int steps=-1;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setTimeinminutes(int timeinminutes) {
            this.timeinminutes = timeinminutes;
            return this;
        }

        public Builder setDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder setWeather(Weather weather) {
            this.weather = weather;
            return this;
        }

        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setSport(Sport sport) {
            this.sport = sport;
            return this;
        }

        public Builder setSteps(int steps) {
            this.steps = steps;
            return this;
        }
        public Log build(){
            if(time == null || rating < 0 || timeinminutes < 0 || distance < 0 || weather == null || weight < 0 || height < 0 || sport == null || steps < 0){
                throw new IllegalArgumentException("All Arguments must be set!");
            }
            return new Log(id,time,rating,timeinminutes,distance,weather,weight,height,sport,steps);
        }
    }

    public JSONObject toJSON() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        JSONObject obj = new JSONObject();
        obj.put("date",getTime().format(format));
        obj.put("time",getTimeinminutes());
        obj.put("rating",getRating());
        obj.put("distance",getDistance());
        obj.put("weather",getWeather().toString());
        obj.put("weight",getWeight());
        obj.put("height",getHeight());
        obj.put("sport",getSport().toString());
        obj.put("steps",getSteps());
        return obj;
    }
}
