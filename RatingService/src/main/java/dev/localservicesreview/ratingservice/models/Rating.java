package dev.localservicesreview.ratingservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "rating")
@Getter
@Setter
public class Rating extends BaseModel{
//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinColumn(name = "service_id", referencedColumnName = "id")
//    private Service service;

    private Integer rating;

    private UUID serviceId;

    private UUID userId;
}
