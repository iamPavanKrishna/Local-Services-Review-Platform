package com.localservicesreview.servicemanagementservice.dtos;

import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SignupResponseDto {

    private String name;
    private String email;
    private String password;
    private RoleDto role;

    }
