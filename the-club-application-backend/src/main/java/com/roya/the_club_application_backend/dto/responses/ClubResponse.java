package com.roya.the_club_application_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubResponse {
    private String clubId;
    private String name;
    private String logo;
    private String description;
    private String ownerId;
    private String defaultRoomId;
    private LocalDateTime createdAt;

}
