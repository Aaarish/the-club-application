package com.roya.the_club_application_backend.dto.requests;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskRequest {
    private String description;
    private LocalDateTime toBeCompletedAt;

}
