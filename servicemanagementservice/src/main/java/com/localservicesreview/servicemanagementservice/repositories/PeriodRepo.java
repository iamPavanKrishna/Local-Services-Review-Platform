package com.localservicesreview.servicemanagementservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.localservicesreview.servicemanagementservice.models.Period;

public interface PeriodRepo extends JpaRepository<Period, UUID> {

    @Query(value = CustomQueries.FIND_ALL_PERIODS_BY_SERVICE_ID, nativeQuery = true)
    Optional<List<Period>> findAllByService_Id(UUID serviceId);
}
