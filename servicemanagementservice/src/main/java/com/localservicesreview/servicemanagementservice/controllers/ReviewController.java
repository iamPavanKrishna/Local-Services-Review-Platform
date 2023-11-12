package com.localservicesreview.servicemanagementservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.localservicesreview.servicemanagementservice.dtos.ReviewDto;
import com.localservicesreview.servicemanagementservice.models.Review;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewDto reviewDto){
        String response = reviewService.createReviewsForServiceId(reviewDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
