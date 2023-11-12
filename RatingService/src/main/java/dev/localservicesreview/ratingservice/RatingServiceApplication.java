package dev.localservicesreview.ratingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Rating Management Service API",
//                version = "1.0",
//                description = "API for managing user ratings for services"
//        )
//)
public class RatingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatingServiceApplication.class, args);
    }

}
