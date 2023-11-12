package dev.localservicesreview.ratingservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RatingDto {
    private UUID id;
    private UUID service_id;
    private UUID user_id;
    private Integer rating;
}
