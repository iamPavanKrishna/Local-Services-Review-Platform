package com.localservicesreview.servicemanagementservice.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.localservicesreview.servicemanagementservice.models.Category;

public interface CategoryRepo extends JpaRepository<Category, UUID> {

    Optional<Category> findById(UUID catId);

    Optional<Category> findByName(String name);
}
