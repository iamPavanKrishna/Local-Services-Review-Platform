package dev.localservicesreview.ratingservice.controllers;

import dev.localservicesreview.ratingservice.dtos.RatingDto;
import dev.localservicesreview.ratingservice.exceptions.BadRequestException;
import dev.localservicesreview.ratingservice.exceptions.InternalServerException;
import dev.localservicesreview.ratingservice.exceptions.NotFoundException;
import dev.localservicesreview.ratingservice.services.NotificationService;
import dev.localservicesreview.ratingservice.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class RatingControllerTest {
    @Mock
    private RatingService ratingService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTotalRatingsByServiceId() throws NotFoundException, InternalServerException, BadRequestException {
        UUID serviceId = UUID.randomUUID();
        Long totalRatings = 10L;

        when(ratingService.getTotalRatingsByServiceId(serviceId)).thenReturn(totalRatings);

        ResponseEntity<Long> response = ratingController.getTotalRatingsByServiceId(serviceId);

        verify(ratingService, times(1)).getTotalRatingsByServiceId(serviceId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert Objects.equals(response.getBody(), totalRatings);
    }

//    @Test
//    public void testGetTotalRatingsByServiceIdWhenServiceIdDoesNotExist() throws NotFoundException, InternalServerException {
//        UUID serviceId = UUID.randomUUID();
//
//        when(ratingService.getTotalRatingsByServiceId(serviceId)).thenThrow(new NotFoundException("Service not found."));
//
//        ResponseEntity<Long> response = ratingController.getTotalRatingsByServiceId(serviceId);
//
//        verify(ratingService, times(1)).getTotalRatingsByServiceId(serviceId);
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }

//    @Test
//    public void testGetTotalRatingsByServiceIdWhenInternalServerExceptionIsThrown() throws NotFoundException, InternalServerException {
//        UUID serviceId = UUID.randomUUID();
//
//        when(ratingService.getTotalRatingsByServiceId(serviceId)).thenThrow(new InternalServerException("Internal Server Exception."));
//
//        ResponseEntity<Long> response = ratingController.getTotalRatingsByServiceId(serviceId);
//
//        verify(ratingService, times(1)).getTotalRatingsByServiceId(serviceId);
//        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
//    }

//    @Test
//    public void testGetTotalRatingsByServiceIdWhenNotFoundExceptionIsThrown() throws NotFoundException, InternalServerException {
//        UUID serviceId = UUID.randomUUID();
//
//        when(ratingService.getTotalRatingsByServiceId(serviceId)).thenThrow(new NotFoundException("Not Found Exception."));
//
//        ResponseEntity<Long> response = ratingController.getTotalRatingsByServiceId(serviceId);
//
//        verify(ratingService, times(1)).getTotalRatingsByServiceId(serviceId);
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }

    @Test
    public void testGetAverageRatingOfService() throws NotFoundException, InternalServerException, BadRequestException {
        UUID serviceId = UUID.randomUUID();
        Double averageRating = 4.5;

        when(ratingService.getAverageRatingOfService(serviceId)).thenReturn(averageRating);

        ResponseEntity<Double> response = ratingController.getAverageRatingOfService(serviceId);

        verify(ratingService, times(1)).getAverageRatingOfService(serviceId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert Objects.equals(response.getBody(), averageRating);
    }

//    @Test
//    public void testGetAverageRatingOfServiceWhenInternalServerExceptionIsThrown() throws NotFoundException, InternalServerException {
//        UUID serviceId = UUID.randomUUID();
//
//        when(ratingService.getAverageRatingOfService(serviceId)).thenThrow(new InternalServerException("Internal Server Exception."));
//
//        ResponseEntity<Double> response = ratingController.getAverageRatingOfService(serviceId);
//
//        verify(ratingService, times(1)).getAverageRatingOfService(serviceId);
//        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
//    }

//    @Test
//    public void testGetAverageRatingOfServiceWhenNotFoundExceptionIsThrown() throws NotFoundException, InternalServerException {
//        UUID serviceId = UUID.randomUUID();
//
//        when(ratingService.getAverageRatingOfService(serviceId)).thenThrow(new NotFoundException("Not Found Exception."));
//
//        ResponseEntity<Double> response = ratingController.getAverageRatingOfService(serviceId);
//
//        verify(ratingService, times(1)).getAverageRatingOfService(serviceId);
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }

//    @Test
//    public void testSubmitRating() throws NotFoundException, InternalServerException, BadRequestException {
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(5);
//
//        RatingDto newRating = new RatingDto();
//        newRating.setId(UUID.randomUUID());
//        newRating.setRating(5);
//
//        when(ratingService.submitRating(ratingDto)).thenReturn(newRating);
//
//        ResponseEntity<RatingDto> response = ratingController.submitRating(ratingDto);
//
//        verify(ratingService, times(1)).submitRating(ratingDto);
//        assert response.getStatusCode() == HttpStatus.OK;
//        assert response.getBody() == newRating;
//    }

//    @Test
//    public void testSubmitRatingWhenRatingIsLessThanOne() throws NotFoundException, InternalServerException, BadRequestException {
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(0);
//
//        RatingDto newRating = new RatingDto();
//        newRating.setId(UUID.randomUUID());
//        newRating.setRating(5);
//
//        when(ratingService.submitRating(ratingDto)).thenThrow(new BadRequestException("Rating must be in range 1 to 5."));
//
//        ResponseEntity<RatingDto> response = ratingController.submitRating(ratingDto);
//
//        verify(ratingService, times(1)).submitRating(ratingDto);
//        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
//    }

//    @Test
//    public void testSubmitRatingWhenRatingIsGreaterThanFive() throws NotFoundException, InternalServerException, BadRequestException {
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(6);
//
//        RatingDto newRating = new RatingDto();
//        newRating.setId(UUID.randomUUID());
//        newRating.setRating(5);
//
//        when(ratingService.submitRating(ratingDto)).thenThrow(new BadRequestException("Rating must be in range 1 to 5."));
//
//        ResponseEntity<RatingDto> response = ratingController.submitRating(ratingDto);
//
//        verify(ratingService, times(1)).submitRating(ratingDto);
//        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
//    }
//
//    @Test
//    public void testSubmitRatingWhenServiceIdDoesNotExist() throws NotFoundException, InternalServerException, BadRequestException {
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(5);
//
//        RatingDto newRating = new RatingDto();
//        newRating.setId(UUID.randomUUID());
//        newRating.setRating(5);
//
//        when(ratingService.submitRating(ratingDto)).thenThrow(new NotFoundException("Service not found."));
//
//        ResponseEntity<RatingDto> response = ratingController.submitRating(ratingDto);
//
//        verify(ratingService, times(1)).submitRating(ratingDto);
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }
//
//    @Test
//    public void testSubmitRatingWhenInternalServerExceptionIsThrown() throws NotFoundException, InternalServerException, BadRequestException {
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(5);
//
//        RatingDto newRating = new RatingDto();
//        newRating.setId(UUID.randomUUID());
//        newRating.setRating(5);
//
//        when(ratingService.submitRating(ratingDto)).thenThrow(new InternalServerException("Internal Server Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.submitRating(ratingDto);
//
//        verify(ratingService, times(1)).submitRating(ratingDto);
//        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
//    }
//
//    @Test
//    public void testSubmitRatingWhenNotFoundExceptionIsThrown() throws NotFoundException, InternalServerException, BadRequestException {
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(5);
//
//        RatingDto newRating = new RatingDto();
//        newRating.setId(UUID.randomUUID());
//        newRating.setRating(5);
//
//        when(ratingService.submitRating(ratingDto)).thenThrow(new NotFoundException("Not Found Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.submitRating(ratingDto);
//
//        verify(ratingService, times(1)).submitRating(ratingDto);
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }
//
//    @Test
//    public void testSubmitRatingWhenBadRequestExceptionIsThrown() throws NotFoundException, InternalServerException, BadRequestException {
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(5);
//
//        RatingDto newRating = new RatingDto();
//        newRating.setId(UUID.randomUUID());
//        newRating.setRating(5);
//
//        when(ratingService.submitRating(ratingDto)).thenThrow(new BadRequestException("Bad Request Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.submitRating(ratingDto);
//
//        verify(ratingService, times(1)).submitRating(ratingDto);
//        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
//    }
//
//    @Test
//    public void testUpdateRating() throws NotFoundException, InternalServerException, BadRequestException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(4);
//
//        RatingDto updatedRating = new RatingDto();
//        updatedRating.setId(ratingId);
//        updatedRating.setRating(4);
//
//        UUID serviceId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(ratingService.updateRating(serviceId, userId, ratingDto.getRating())).thenReturn(updatedRating);
//
//        ResponseEntity<RatingDto> response = ratingController.updateRating(ratingDto);
//
//        verify(ratingService, times(1)).updateRating(serviceId, userId, ratingDto.getRating());
//        assert response.getStatusCode() == HttpStatus.OK;
//        assert response.getBody() == updatedRating;
//    }
//
//    @Test
//    public void testUpdateRatingWhenRatingIdDoesNotExist() throws NotFoundException, InternalServerException, BadRequestException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(4);
//
//        RatingDto updatedRating = new RatingDto();
//        updatedRating.setId(ratingId);
//        updatedRating.setRating(4);
//
//        UUID serviceId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(ratingService.updateRating(serviceId, userId, ratingDto.getRating())).thenThrow(new NotFoundException("Rating not found."));
//
//        ResponseEntity<RatingDto> response = ratingController.updateRating(ratingDto);
//
//        verify(ratingService, times(1)).updateRating(serviceId, userId, ratingDto.getRating());
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }
//
//    @Test
//    public void testUpdateRatingWhenInternalServerExceptionIsThrown() throws NotFoundException, InternalServerException, BadRequestException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(4);
//
//        RatingDto updatedRating = new RatingDto();
//        updatedRating.setId(ratingId);
//        updatedRating.setRating(4);
//
//        UUID serviceId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(ratingService.updateRating(serviceId, userId, ratingDto.getRating())).thenThrow(new InternalServerException("Internal Server Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.updateRating(ratingDto);
//
//        verify(ratingService, times(1)).updateRating(serviceId, userId, ratingDto.getRating());
//        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
//    }
//
//    @Test
//    public void testUpdateRatingWhenNotFoundExceptionIsThrown() throws NotFoundException, InternalServerException, BadRequestException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(4);
//
//        RatingDto updatedRating = new RatingDto();
//        updatedRating.setId(ratingId);
//        updatedRating.setRating(4);
//
//        UUID serviceId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(ratingService.updateRating(serviceId, userId, ratingDto.getRating())).thenThrow(new NotFoundException("Not Found Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.updateRating(ratingDto);
//
//        verify(ratingService, times(1)).updateRating(serviceId, userId, ratingDto.getRating());
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }
//
//    @Test
//    public void testUpdateRatingWhenBadRequestExceptionIsThrown() throws NotFoundException, InternalServerException, BadRequestException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto ratingDto = new RatingDto();
//        ratingDto.setRating(4);
//
//        RatingDto updatedRating = new RatingDto();
//        updatedRating.setId(ratingId);
//        updatedRating.setRating(4);
//
//        UUID serviceId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(ratingService.updateRating(serviceId, userId, ratingDto.getRating())).thenThrow(new BadRequestException("Bad Request Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.updateRating(ratingDto);
//
//        verify(ratingService, times(1)).updateRating(serviceId, userId, ratingDto.getRating());
//        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
//    }

//    @Test
//    public void testDeleteRating() throws NotFoundException, InternalServerException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto deletedRating = new RatingDto();
//        deletedRating.setId(ratingId);
//
//        when(ratingService.deleteRating(ratingId)).thenReturn(deletedRating);
//
//        ResponseEntity<RatingDto> response = ratingController.deleteRating(ratingId);
//
//        verify(ratingService, times(1)).deleteRating(ratingId);
//        assert response.getStatusCode() == HttpStatus.OK;
//        assert response.getBody() == deletedRating;
//    }

//    @Test
//    public void testDeleteRatingWhenRatingIdDoesNotExist() throws NotFoundException, InternalServerException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto deletedRating = new RatingDto();
//        deletedRating.setId(ratingId);
//
//        when(ratingService.deleteRating(ratingId)).thenThrow(new NotFoundException("Rating not found."));
//
//        ResponseEntity<RatingDto> response = ratingController.deleteRating(ratingId);
//
//        verify(ratingService, times(1)).deleteRating(ratingId);
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }
//
//    @Test
//    public void testDeleteRatingWhenInternalServerExceptionIsThrown() throws NotFoundException, InternalServerException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto deletedRating = new RatingDto();
//        deletedRating.setId(ratingId);
//
//        when(ratingService.deleteRating(ratingId)).thenThrow(new InternalServerException("Internal Server Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.deleteRating(ratingId);
//
//        verify(ratingService, times(1)).deleteRating(ratingId);
//        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
//    }
//
//    @Test
//    public void testDeleteRatingWhenNotFoundExceptionIsThrown() throws NotFoundException, InternalServerException {
//        UUID ratingId = UUID.randomUUID();
//        RatingDto deletedRating = new RatingDto();
//        deletedRating.setId(ratingId);
//
//        when(ratingService.deleteRating(ratingId)).thenThrow(new NotFoundException("Not Found Exception."));
//
//        ResponseEntity<RatingDto> response = ratingController.deleteRating(ratingId);
//
//        verify(ratingService, times(1)).deleteRating(ratingId);
//        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
//    }
}
