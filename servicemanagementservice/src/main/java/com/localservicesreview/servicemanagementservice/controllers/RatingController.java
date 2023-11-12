package com.localservicesreview.servicemanagementservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.localservicesreview.servicemanagementservice.dtos.RatingDto;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.rating.RatingService;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {
    private RatingService ratingService;
    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingDto> submitRatingForServiceId(@RequestBody RatingDto ratingDto){
        RatingDto newRatingDto = ratingService.submitRatingForServiceId(ratingDto);
        return new ResponseEntity<>(newRatingDto, HttpStatus.CREATED);
    }

}
