package com.localservicesreview.servicemanagementservice.dtos;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadPhotoResponseDto {
    private UUID serviceId;
    private List<Photo> photos;

    private class Photo{
        private UUID photoId;
        private String url;
    }
}

