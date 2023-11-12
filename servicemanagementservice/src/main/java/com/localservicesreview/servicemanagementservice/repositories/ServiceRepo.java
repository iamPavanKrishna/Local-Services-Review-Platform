package com.localservicesreview.servicemanagementservice.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.localservicesreview.servicemanagementservice.models.Service;

@Repository
public interface ServiceRepo extends JpaRepository<Service, UUID> {
    List<Service> findAllByCategories_name(String name);
    List<Service> findAllByNameContainingIgnoreCase(String name);
    List<Service>  findAllByFormattedAddressContainsIgnoreCase(String address);
    List<Service> findAllByCategories_name_AndRatingEquals(String categoryName, double rating);
    List<Service> findAllByNameContainingIgnoreCaseAndCategories_name(String serviceName, String categoryName);
    List<Service> findAllByNameContainingIgnoreCaseAndCategories_nameIn(String serviceName, List<String> categories);
    List<Service> findAllByNameContainingIgnoreCaseAndRatingEquals(String serviceName, double rating);
    List<Service> findAllByNameContainingIgnoreCaseAndRatingIn(String serviceName, List<Double> ratings);
    List<Service> findAllByFormattedAddressContainsIgnoreCaseAndCategories_name(String address, String categoryName);
    List<Service> findAllByFormattedAddressContainsIgnoreCaseAndCategories_nameIn(String address, List<String> categories);
    List<Service> findAllByFormattedAddressContainsIgnoreCaseAndRatingEquals(String address, double rating);
    List<Service> findAllByFormattedAddressContainsIgnoreCaseAndRatingIn(String address, List<Double> ratings);
    List<Service> findAllByNameContainingIgnoreCaseAndCategories_nameAndRatingEquals(String address, String categoryName, double rating);
    List<Service> findAllByNameContainingIgnoreCaseAndCategories_nameInAndRatingIn(String address, List<String> categories, List<Double> ratings);
    List<Service> findAllByFormattedAddressContainsIgnoreCaseAndCategories_nameAndRatingEquals(String address, String categoryName, double rating);
    List<Service> findAllByFormattedAddressContainsIgnoreCaseAndCategories_nameInAndRatingIn(String address, List<String> categories, List<Double> ratings);

}
