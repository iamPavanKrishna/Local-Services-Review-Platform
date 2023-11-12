package com.localservicesreview.servicemanagementservice.models;

import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews.GetReviewDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service extends BaseModel {
    private UUID creatorId;
    private String name;
    private String phoneNumber;
    private String email;
    private String formattedAddress;
    private String website;
    private String ownerName;
    private String googleMapLink;
    private ServiceState serviceState;
    private double rating;
    private Long totalUserRatings;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Location location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name="service_category",
        joinColumns = @JoinColumn(name="service_id"),
        inverseJoinColumns = @JoinColumn(name="category_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Period> periods;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}
