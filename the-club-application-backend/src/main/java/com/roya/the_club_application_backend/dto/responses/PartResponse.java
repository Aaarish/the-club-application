package com.roya.the_club_application_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PartResponse {
    private String partId;
    private String taskId;
    private String memberId;
    private LocalDateTime completedAt;
    private String note;

}
