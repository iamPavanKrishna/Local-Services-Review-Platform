package com.localservicesreview.servicemanagementservice.dtos;

import java.util.UUID;
import com.localservicesreview.servicemanagementservice.models.AttachmentTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDto {
    UUID id;
    UUID userId;
    UUID serviceId;
    String text;
    AttachmentTypeEnum attachmentTypeEnum;
    String attachmentUrl;
    int usefulCount;
    int funnyCount;
    int coolCount;

}
