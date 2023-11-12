package com.localservicesreview.servicemanagementservice.dtos;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.localservicesreview.servicemanagementservice.models.Location;
import com.localservicesreview.servicemanagementservice.models.Period;
import com.localservicesreview.servicemanagementservice.models.ServiceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateServiceRequestDto {
    private String name;

    @JsonProperty("creator_id")
    private UUID creatorId;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("email_id")
    private String emailId;
    private Address address;
    private String website;
    @JsonProperty("owner_name")
    private String ownerName;
    @JsonProperty("category_ids")
    private List<UUID> categories;
    @JsonProperty("google_map_link")
    private String googleMapLink;
    @JsonProperty("service_state")
    private ServiceState serviceState;
    @JsonProperty("weekly_open_periods")
    private List<Period> weeklyOpenPeriods;
    private Location location;

    @Getter
    @Setter
    public class Address{
        @JsonProperty("address_line1")
        private String addressLine1;
        @JsonProperty("address_line2")
        private String addressLine2;
        private String city;
        @JsonProperty("postal_code")
        private String postalCode;
        private String country;
    }
}
