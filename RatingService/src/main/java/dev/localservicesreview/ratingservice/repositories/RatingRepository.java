package dev.localservicesreview.ratingservice.repositories;

import dev.localservicesreview.ratingservice.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    Long countByServiceId(UUID serviceId);

    List<Rating> findAllByServiceId(UUID serviceId);

    Rating findByServiceIdAndUserId(UUID serviceId, UUID userId);
}
