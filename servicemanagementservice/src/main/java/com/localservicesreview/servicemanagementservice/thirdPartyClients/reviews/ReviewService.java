package com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.localservicesreview.servicemanagementservice.dtos.ReviewDto;
import com.localservicesreview.servicemanagementservice.models.Review;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews.exceptions.ReviewNotFound;

@Service
public class ReviewService {

    private RestTemplate restTemplate;
    private String reviewsUrl;
    private String getReviewEndpoint;
    public ReviewService (RestTemplateBuilder restTemplateBuilder, @Value("${reviews.url}") String reviewsUrl, @Value("${reviews.api.get.endpoint}") String getReviewEndpoint){
        restTemplate = restTemplateBuilder.build();
        this.reviewsUrl = reviewsUrl;
        this.getReviewEndpoint = getReviewEndpoint;
    }

    public List<Review> getReviewsByServiceId(UUID serviceId) throws ReviewNotFound {
        ResponseEntity<ReviewDto[]> response = restTemplate.getForEntity(reviewsUrl+getReviewEndpoint+"/"+serviceId, ReviewDto[].class, serviceId);
        if(response.getBody() == null){
            throw new ReviewNotFound(String.format("Reviews not found for service ", serviceId));
        }

        List<Review> reviews = new ArrayList<>();
        for(ReviewDto reviewDto : response.getBody()){
            reviews.add(Review.from(reviewDto));
        }
        return reviews;
    }

    public String createReviewsForServiceId(ReviewDto reviewDto){
        ResponseEntity<String> response = restTemplate.postForEntity(reviewsUrl, reviewDto, String.class);
        return response.getBody();
    }

}
