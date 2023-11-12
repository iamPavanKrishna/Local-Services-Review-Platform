package dev.localservicesreview.ratingservice.services;

import dev.localservicesreview.ratingservice.dtos.RatingDto;
import dev.localservicesreview.ratingservice.dtos.RatingRequestDto;
import dev.localservicesreview.ratingservice.exceptions.BadRequestException;
import dev.localservicesreview.ratingservice.exceptions.InternalServerException;
import dev.localservicesreview.ratingservice.exceptions.NotFoundException;

import java.util.UUID;

//@Service
public interface RatingService {
    public RatingDto getRatingById(UUID rating_id) throws NotFoundException, InternalServerException;
    public Long getTotalRatingsByServiceId(UUID service_id) throws NotFoundException, InternalServerException;
    public Double getAverageRatingOfService(UUID service_id) throws NotFoundException, InternalServerException;
    public RatingDto submitRating(RatingRequestDto ratingRequestDto) throws NotFoundException, InternalServerException, BadRequestException;
    public RatingDto updateRating(RatingRequestDto ratingRequestDto) throws NotFoundException, InternalServerException, BadRequestException;
    public RatingDto deleteRating(RatingRequestDto ratingRequestDto) throws NotFoundException, InternalServerException;
}
