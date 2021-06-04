package at.technikum.restserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;


@SpringBootApplication(scanBasePackages = "at.technikum")
public class RestServer {
    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }
}


/*
* curl -X POST http://localhost:8089/resources/tours --header "Content-Type: application/json" -d "{\"name\": \"curl Tour\",\"description\": \"test für den restserver\",\"start\": \"Budapest\",\"finish\": \"Wien\",\"distance\": 14}"
* curl -X POST http://localhost:8089/resources/tours/20/logs --header "Content-Type: application/json" -d "{\"time\": \"2021-04-07T12:12:00\",\"rating\": 2,\"timeinminutes\": 12,\"distance\": 12,\"weather\": \"Rain\",\"weight\": 12,\"height\": 12,\"sport\": \"Bicycle\",\"steps\": 12}"
* curl -X PUT http://localhost:8089/resources/tours/20/logs/25 --header "Content-Type: application/json" -d "{\"Datum\": \"07.04.2021, 12:12\",\"Rating\": 5,\"Zeit\": 240,\"Distanz\": 20,\"Weather\": \"Rain\",\"Weight\": 80,\"Height\": 180,\"Sport\": \"Bicycle\",\"Steps\": 1200}"
* curl -X PUT http://localhost:8089/resources/tours/20 --header "Content-Type: application/json" -d "{\"Tourname\": \"curl Tour geupdated\",\"Beschreibung\": \"test2 für den restserver\",\"Von\": \"Budapest\",\"Bis\": \"Linz\",\"Distanz\": 140}"
* curl -X DELETE http://localhost:8089/resources/tours/20/logs/25
* */