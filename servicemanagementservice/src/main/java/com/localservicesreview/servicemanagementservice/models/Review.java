package com.localservicesreview.servicemanagementservice.models;

import java.util.Date;
import java.util.UUID;
import com.localservicesreview.servicemanagementservice.dtos.ReviewDto;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews.GetReviewDto;
import jakarta.persistence.Entity;
import jakarta.transaction.UserTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseModel{

    private UUID userId;
    private UUID serviceId;
    private Integer stars;
    private String text;
    private int usefulCount;
    private int funnyCount;
    private int coolCount;
    private String attachmentType;
    private String attachmentUrl;

    public static Review from(ReviewDto reviewDto){
        Review newReview = new Review();
        newReview.setId(reviewDto.getId());
        newReview.setUserId(reviewDto.getUserId());
        newReview.setServiceId(reviewDto.getServiceId());
        newReview.setText(reviewDto.getText());
        newReview.setUsefulCount(reviewDto.getUsefulCount());
        newReview.setFunnyCount(reviewDto.getFunnyCount());
        newReview.setCoolCount(reviewDto.getCoolCount());
        if (reviewDto.getAttachmentTypeEnum()!=null)
            newReview.setAttachmentType(reviewDto.getAttachmentTypeEnum().toString());
        newReview.setAttachmentUrl(reviewDto.getAttachmentUrl());
        return newReview;
    }


}
