package com.roya.the_club_application_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private String roomId;
    private String name;
    private String description;
    private String clubId;
    private LocalDateTime createdAt;

}
