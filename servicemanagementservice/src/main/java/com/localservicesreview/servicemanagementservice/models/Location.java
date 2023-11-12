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
public class Location extends BaseModel{
//    @OneToOne
//    @JoinColumn(name = "service_id")
//    private Service service;
    private double latitude;
    private double longitude;
}
