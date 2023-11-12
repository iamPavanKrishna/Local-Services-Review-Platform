package dev.localservicesreview.ratingservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Getter
@Setter
public class RatingRequestDto {
    private UUID service_id;
    private UUID user_id;
    private Integer rating;
}
