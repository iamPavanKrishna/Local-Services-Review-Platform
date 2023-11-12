package com.localservicesreview.servicemanagementservice.thirdPartyClients.rating;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.localservicesreview.servicemanagementservice.dtos.RatingDto;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews.exceptions.ReviewNotFound;

@Service
public class RatingService {

    private RestTemplate restTemplate;
    private String ratingUrl;
    private String getAvgRatingEndpoint;
    private String getTotalRatingEndpoint;
    public RatingService (RestTemplateBuilder restTemplateBuilder, @Value("${ratings.url}") String reviewsUrl, @Value("${ratings.api.avgRating.endpoint}") String getAvgRating, @Value("${ratings.api.totalRating.endpoint}") String getTotalRating){
        restTemplate = restTemplateBuilder.build();
        this.ratingUrl = reviewsUrl;
        this.getAvgRatingEndpoint = getAvgRating;
        this.getTotalRatingEndpoint = getTotalRating;
    }

    public Double getRatingsByServiceId(UUID serviceId) throws ReviewNotFound {
        ResponseEntity<Double> response = restTemplate.getForEntity(ratingUrl+getAvgRatingEndpoint+"/"+serviceId, Double.class);
        if(response.getBody() == null){
            throw new ReviewNotFound(String.format("Ratings not found for service ", serviceId));
        }
        return response.getBody();
    }

    public Long getTotalRatingsByServiceId(UUID serviceId) throws ReviewNotFound {
        ResponseEntity<Long> response = restTemplate.getForEntity(ratingUrl+getTotalRatingEndpoint+"/"+serviceId, Long.class);
        if(response.getBody() == null){
            throw new ReviewNotFound(String.format("Ratings not found for service ", serviceId));
        }
        return response.getBody();
    }

    public RatingDto submitRatingForServiceId(RatingDto ratingDto){
        ResponseEntity<RatingDto> response = restTemplate.postForEntity(ratingUrl, ratingDto, RatingDto.class);
        return response.getBody();
    }

}
