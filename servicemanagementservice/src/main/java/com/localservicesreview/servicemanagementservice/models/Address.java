package com.localservicesreview.servicemanagementservice.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseModel{
//    @OneToOne(mappedBy = "address")
//    @JoinColumn(name = "service_id")
//    private Service service;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String country;


}
