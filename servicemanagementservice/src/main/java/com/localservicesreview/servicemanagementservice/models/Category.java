package com.localservicesreview.servicemanagementservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class Category extends BaseModel {
    private String name;

//    @ManyToMany
//    private List<Service> services;

    public Category(String name){
        this.name = name;
    }

}
