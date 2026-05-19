package com.roya.the_club_application_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private String taskId;
    private String roomId;
    private String description;
    private String taskCreatorId;
    private LocalDateTime createdAt;
    private LocalDateTime toBeCompletedAt;

}
