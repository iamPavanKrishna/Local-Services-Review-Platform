package com.localservicesreview.servicemanagementservice.dtos;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.localservicesreview.servicemanagementservice.models.Address;
import com.localservicesreview.servicemanagementservice.models.Category;
import com.localservicesreview.servicemanagementservice.models.Location;
import com.localservicesreview.servicemanagementservice.models.Period;
import com.localservicesreview.servicemanagementservice.models.Review;
import com.localservicesreview.servicemanagementservice.models.Service;
import com.localservicesreview.servicemanagementservice.models.ServiceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetServiceResponseDto {

    @JsonProperty("service_id")
    private UUID serviceId;
    @JsonProperty("creator_id")
    private UUID creatorId;
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("email_id")
    private String emailId;
    private Address address;
    @JsonProperty("formatted_address")
    private String formattedAddress;
    private String website;
    @JsonProperty("owner_name")
    private String ownerName;
    @JsonProperty("category_ids")
    private List<Category> categories;
    @JsonProperty("google_map_link")
    private String googleMapLink;
    @JsonProperty("service_state")
    private ServiceState serviceState;
    @JsonProperty("weekly_open_periods")
    private List<Period> periods;
    private Location location;
    @JsonProperty("open_now")
    private boolean openNow;
    private double rating;
    @JsonProperty("total_user_ratings")
    private Long totalUserRatings;
    private List<Review> reviews;

    public static GetServiceResponseDto from(Service service) {
        GetServiceResponseDto serviceResponseDto = new GetServiceResponseDto();
        serviceResponseDto.setServiceId(service.getId());
        serviceResponseDto.setName(service.getName());
        serviceResponseDto.setPhoneNumber(service.getPhoneNumber());
        serviceResponseDto.setEmailId(service.getEmail());
        serviceResponseDto.setAddress(service.getAddress());
        serviceResponseDto.setFormattedAddress(service.getFormattedAddress());
        serviceResponseDto.setWebsite(service.getWebsite());
        serviceResponseDto.setOwnerName(service.getOwnerName());
        serviceResponseDto.setCreatorId(service.getCreatorId());
        serviceResponseDto.setCategories(service.getCategories());
        serviceResponseDto.setGoogleMapLink(service.getGoogleMapLink());
        serviceResponseDto.setServiceState(service.getServiceState());
        serviceResponseDto.setPeriods(service.getPeriods());
        serviceResponseDto.setLocation(service.getLocation());
        serviceResponseDto.setRating(service.getRating());
        serviceResponseDto.setTotalUserRatings(service.getTotalUserRatings());
        serviceResponseDto.setReviews(service.getReviews());
        return serviceResponseDto;
    }

}
