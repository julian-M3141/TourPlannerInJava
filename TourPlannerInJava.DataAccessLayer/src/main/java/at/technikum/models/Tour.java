package at.technikum.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


@Getter
@Setter
@AllArgsConstructor
public class Tour {

    //private member variables
    private int id;
    private String name;
    private String description;
    private String start;
    private String finish;
    private int distance;
    private String image;
    private ArrayList<Log> logs;

    public Tour() {
        super();
    }

    public Tour(String name, String description, String start, String finish, int distance) {
        this(-1,name,description,start,finish,distance,"",new ArrayList<>());
    }


    //builder pattern
    public static class Builder {
        private int id=-1;

        private String name=null;
        private String description=null;
        private String start=null;
        private String finish=null;
        private int distance=-1;
        private String image=null;
        private ArrayList<Log> logs=null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder setFinish(String finish) {
            this.finish = finish;
            return this;
        }

        public Builder setStart(String start) {
            this.start = start;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setImage(String image) {
            this.image = image;
            return this;
        }
        public Builder setLogs(ArrayList<Log> logs) {
            this.logs = logs;
            return this;
        }

        public Tour build(){
            if(name == null || description == null || start == null || finish == null || distance < 0){
                throw new IllegalArgumentException("All instances have to be set!");
            }
            if(logs==null)
                logs = new ArrayList<Log>();
            return new Tour(id,name,description,start,finish,distance,image,logs);
        }

    };

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("tourname",getName());
        obj.put("distance",getDistance());
        obj.put("start",getStart());
        obj.put("finish",getFinish());
        obj.put("description",getDescription());
        obj.put("image",getImage());
        JSONArray logs = new JSONArray();
        for(Log log : getLogs()){
            logs.put(log.toJSON());
        }
        obj.put("logs",logs);
        return obj;
    }

}
