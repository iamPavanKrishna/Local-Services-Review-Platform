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
public class Photo extends BaseModel{

//    @ManyToOne
//    @JoinTable(
//        name="service_photo",
//        joinColumns = @JoinColumn(name="photo_id"),
//        inverseJoinColumns = @JoinColumn(name="service_id")
//    )
//    private Service service;
    private String url;


}
