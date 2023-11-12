package dev.localservicesreview.ratingservice.controllers;

import dev.localservicesreview.ratingservice.dtos.RatingDto;
import dev.localservicesreview.ratingservice.dtos.RatingRequestDto;
import dev.localservicesreview.ratingservice.exceptions.BadRequestException;
import dev.localservicesreview.ratingservice.exceptions.InternalServerException;
import dev.localservicesreview.ratingservice.exceptions.NotFoundException;
import dev.localservicesreview.ratingservice.services.TPANotificationService;
import dev.localservicesreview.ratingservice.services.RatingService;
import dev.localservicesreview.ratingservice.thirdpartyclients.notificationSvc.Channel;
import dev.localservicesreview.ratingservice.thirdpartyclients.notificationSvc.NotificationRequestDto;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;
    private final TPANotificationService notificationService;

    public RatingController(RatingService ratingService,
                            TPANotificationService notificationService) {
        this.ratingService = ratingService;
        this.notificationService = notificationService;
    }

    @GetMapping("/getTotalServiceRatings/{service_id}")
    public ResponseEntity<Long> getTotalRatingsByServiceId(
            @PathVariable("service_id") UUID service_id) throws InternalServerException, NotFoundException, BadRequestException {
        if(service_id == null || service_id.toString().isEmpty()){
            throw new BadRequestException("Service id cannot be null or empty");
        }
        Long totalRatings = ratingService.getTotalRatingsByServiceId(service_id);
        return new ResponseEntity<>(totalRatings, HttpStatus.OK);
    }

    @GetMapping("/getAverageServiceRatings/{service_id}")
    public ResponseEntity<Double> getAverageRatingOfService(
            @PathVariable("service_id") UUID service_id) throws InternalServerException, NotFoundException, BadRequestException {
        if(service_id == null || service_id.toString().isEmpty()){
            throw new BadRequestException("Service id cannot be null or empty");
        }
        Double averageRating = ratingService.getAverageRatingOfService(service_id);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RatingDto> submitRating(@RequestBody RatingRequestDto ratingReqDto)
            throws InternalServerException, NotFoundException, BadRequestException {
        if(ratingReqDto.getService_id() == null || ratingReqDto.getService_id().toString().isEmpty()){
            throw new BadRequestException("Service id cannot be null or empty");
        }
        if(ratingReqDto.getUser_id() == null || ratingReqDto.getUser_id().toString().isEmpty()){
            throw new BadRequestException("User id cannot be null or empty");
        }
        if(ratingReqDto.getRating() == null){
            throw new BadRequestException("Rating cannot be null or less than 0");
        }

        RatingDto newRating = ratingService.submitRating(ratingReqDto);

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setService_id(newRating.getService_id());
        notificationRequestDto.setData("New rating " + newRating.getRating() +
                " received for service.");
        notificationRequestDto.setType("alert");
        notificationRequestDto.setSubject("New rating received");
        notificationRequestDto.setChannel(Channel.EMAIL);
        notificationRequestDto.setImage_url(null);

        notificationService.sendNotification(notificationRequestDto, newRating.getUser_id());

        // Return a response with the newly created rating
        return new ResponseEntity<>(newRating, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RatingDto> updateRating(
            @RequestBody RatingRequestDto ratingReqDto) throws InternalServerException, NotFoundException, BadRequestException {

        if(ratingReqDto.getService_id() == null || ratingReqDto.getService_id().toString().isEmpty()){
            throw new BadRequestException("Service id cannot be null or empty");
        }
        if(ratingReqDto.getUser_id() == null || ratingReqDto.getUser_id().toString().isEmpty()){
            throw new BadRequestException("User id cannot be null or empty");
        }
        if(ratingReqDto.getRating() == null){
            throw new BadRequestException("Rating cannot be null or less than 0");
        }
        // Call the service to submit the rating
        RatingDto updatedRating = ratingService.updateRating(ratingReqDto);

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setService_id(updatedRating.getService_id());
        notificationRequestDto.setData("Rating update " + updatedRating.getRating() +
                " received for service.");
        notificationRequestDto.setType("alert");
        notificationRequestDto.setSubject("Rating update received");
        notificationRequestDto.setChannel(Channel.EMAIL);
        notificationRequestDto.setImage_url(null);

        notificationService.sendNotification(notificationRequestDto, updatedRating.getUser_id());

        // Return a response with the newly created rating
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<RatingDto> deleteRating(@RequestBody RatingRequestDto ratingReqDto)
            throws NotFoundException, InternalServerException, BadRequestException {

        if(ratingReqDto.getService_id() == null || ratingReqDto.getService_id().toString().isEmpty()){
            throw new BadRequestException("Service id cannot be null or empty");
        }
        if(ratingReqDto.getUser_id() == null || ratingReqDto.getUser_id().toString().isEmpty()){
            throw new BadRequestException("User id cannot be null or empty");
        }
        // Call the service to submit the rating
        RatingDto deletedRating = ratingService.deleteRating(ratingReqDto);

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setService_id(deletedRating.getService_id());
        notificationRequestDto.setData("Rating delete " + deletedRating.getRating() +
                " received for service.");
        notificationRequestDto.setType("alert");
        notificationRequestDto.setSubject("Rating delete received");
        notificationRequestDto.setChannel(Channel.EMAIL);
        notificationRequestDto.setImage_url(null);

        notificationService.sendNotification(notificationRequestDto, deletedRating.getUser_id());

        // Return a response with the deleted rating
        return new ResponseEntity<>(deletedRating, HttpStatus.OK);
    }
}
