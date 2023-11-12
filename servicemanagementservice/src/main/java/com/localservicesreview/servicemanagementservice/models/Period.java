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
public class Period extends BaseModel {
//    @ManyToOne
//    @JoinTable(
//        name="service_period",
//        joinColumns = @JoinColumn(name="period_id"),
//        inverseJoinColumns = @JoinColumn(name="service_id")
//    )
//    private Service service;
    private int day;
    private int open;
    private int close;

}
