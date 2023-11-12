package dev.localservicesreview.ratingservice.services;

import dev.localservicesreview.ratingservice.dtos.RatingDto;
import dev.localservicesreview.ratingservice.dtos.RatingRequestDto;
import dev.localservicesreview.ratingservice.exceptions.BadRequestException;
import dev.localservicesreview.ratingservice.exceptions.InternalServerException;
import dev.localservicesreview.ratingservice.exceptions.NotFoundException;
import dev.localservicesreview.ratingservice.models.Rating;
import dev.localservicesreview.ratingservice.repositories.RatingRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("selfRatingService")
@Primary
public class SelfRatingService implements RatingService {
    private final RatingRepository ratingRepository;
//    TPAServiceManagementService serviceManagementService;
    TPANotificationService notificationService;
//    TPAUserService userService;

    public SelfRatingService(RatingRepository ratingRepository,
                             TPANotificationService notificationService) {
        this.ratingRepository = ratingRepository;
//        this.serviceManagementService = serviceManagementService;
        this.notificationService = notificationService;
//        this.userService = userService;
    }

    @Override
    public RatingDto getRatingById(UUID rating_id) throws NotFoundException, InternalServerException {
        try {
            Rating rating = ratingRepository.findById(rating_id)
                    .orElseThrow(() -> new NotFoundException("Rating with id: " + rating_id + " does not exist."));
            return convertRatingToRatingDto(rating);
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new InternalServerException("Some error occurred at rating service.");
        }
    }

    @Override
    public Long getTotalRatingsByServiceId(UUID service_id) {
        return ratingRepository.countByServiceId(service_id);
    }

    @Override
    public Double getAverageRatingOfService(UUID service_id) {
        // Get all ratings for the service
        List<Rating> ratingsList = ratingRepository.findAllByServiceId(service_id);
        Integer[] ratings = new Integer[ratingsList.size()];
        for(int i = 0; i < ratingsList.size(); i++) {
            ratings[i] = ratingsList.get(i).getRating();
        }
        // Calculate the average rating
        return calculateAverageRating(ratings);
    }

    @Override
    public RatingDto submitRating(RatingRequestDto ratingRequestDto)
            throws BadRequestException {
        if(ratingRequestDto.getRating() < 1 || ratingRequestDto.getRating() > 5) {
            throw new BadRequestException("Rating must be in range 1 to 5.");
        }

        // Check if rating exist with same combination of service id and user id
        Rating existingRatingObj = ratingRepository.findByServiceIdAndUserId(ratingRequestDto.getService_id(), ratingRequestDto.getUser_id());
        if(existingRatingObj != null && existingRatingObj.getId() != null) {
            throw new BadRequestException("Rating already exist with same combination of given service id and user id.");
        }

        Rating ratingObj = convertRatingRequestDtoToRating(ratingRequestDto);
        Rating createdRating = ratingRepository.save(ratingObj);

        return convertRatingToRatingDto(createdRating);
    }

    @Override
    public RatingDto updateRating(RatingRequestDto ratingRequestDto)
            throws NotFoundException, InternalServerException, BadRequestException {
        if(ratingRequestDto.getRating() < 1 || ratingRequestDto.getRating() > 5) {
            throw new BadRequestException("Rating must be in range 1 to 5.");
        }
        try {
            UUID serviceId = ratingRequestDto.getService_id();
            UUID userId = ratingRequestDto.getUser_id();
            Rating ratingObj = ratingRepository.findByServiceIdAndUserId(serviceId, userId);

            if(ratingObj == null || ratingObj.getId() == null) {
                throw new NotFoundException(
                        "Rating with service id: " + serviceId + " and user id: " + userId + " does not exist.");
            }

            ratingObj.setRating(ratingRequestDto.getRating());
            Rating updatedRating = ratingRepository.save(ratingObj);
            return convertRatingToRatingDto(updatedRating);
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new InternalServerException("Some error occurred at rating service.");
        }
    }

    @Override
    public RatingDto deleteRating(RatingRequestDto ratingRequestDto)
    throws NotFoundException, InternalServerException{
        try {
            UUID serviceId = ratingRequestDto.getService_id();
            UUID userId = ratingRequestDto.getUser_id();
            Rating ratingObj = ratingRepository.findByServiceIdAndUserId(serviceId, userId);

            if(ratingObj == null || ratingObj.getId() == null) {
                throw new NotFoundException(
                        "Rating with service id: " + serviceId + " and user id: " + userId + " does not exist.");
            }

            ratingRepository.delete(ratingObj);
            return convertRatingToRatingDto(ratingObj);
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new InternalServerException("Some error occurred at rating service.");    }
    }

    public RatingDto convertRatingToRatingDto(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(rating.getId());
        ratingDto.setRating(rating.getRating());
        ratingDto.setService_id(rating.getServiceId());
        ratingDto.setUser_id(rating.getUserId());
        return ratingDto;
    }

    public Rating convertRatingRequestDtoToRating(RatingRequestDto ratingRequestDto) {
        Rating rating = new Rating();
        rating.setRating(ratingRequestDto.getRating());
        rating.setServiceId(ratingRequestDto.getService_id());
        rating.setUserId(ratingRequestDto.getUser_id());

        return rating;
    }

    public double calculateAverageRating(Integer[] ratings) {
        // Calculate the sum of all ratings
        double sum = 0;
        int count = 0; // Number of valid ratings within the range [1, 5]

        for (double rating : ratings) {
            if (rating >= 1 && rating <= 5) {
                sum += rating;
                count++;
            }
        }

        if (count == 0) {
            // Return a default value
            return 0.0;
        }

        // Calculate the average within the range [1, 5]
        double average = sum / count;

        return Math.min(5.0, Math.max(1.0, average)); // Ensure the calculated average is within the range [1, 5]
    }
}
