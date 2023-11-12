package com.shk.userservice.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendNotificationRequestDto {
  private String type;
  private String channel;
  private String subject;
  private String data;
}
