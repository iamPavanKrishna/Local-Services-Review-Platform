package com.localservicesreview.notificationservice.repositories;


import com.localservicesreview.notificationservice.models.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {

//    @Modifying
//    @Query(value = "DELETE FROM UserPreference u WHERE u.userId =:userid and u.serviceId IN (:serviceids)", nativeQuery = true)
    @Transactional
    Optional<List<UserPreference>> deleteByServiceIdInAndUserId(@Param("serviceids") List<UUID> serviceIds, @Param("userid") UUID userId);

    List<UserPreference> findOneByUserId(UUID uuid);
    @Transactional
    Optional<List<UserPreference>> findByServiceIdAndSubscribed(UUID uuid, boolean b);
}
