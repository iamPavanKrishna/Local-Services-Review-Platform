package com.localservicesreview.notificationservice.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String userId;
    private String email;
    private String name;
}
