package com.localservicesreview.servicemanagementservice.dtos;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {
    private UUID id;
    private UUID service_id;
    private UUID user_id;
    private Integer rating;
}
