package com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews;

import java.util.Date;
import java.util.UUID;
import com.localservicesreview.servicemanagementservice.models.AttachmentTypeEnum;

public class GetReviewDto {
    private UUID userId; // TODO: change it to user object
    private UUID serviceId; // TODO: change it to service object
    private Integer stars;
    private Date createdAt;

    private Date updatedAt;

    private String text;

    private String useful;

    private String funny;

    private String cool;

    private AttachmentTypeEnum attachmentType;

    private String attachmentUrl;

}
