package com.roya.the_club_application_backend.dto.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEvent {
    private String taskId;
    private String roomId;
    private LocalDateTime expiredAt;
    Map<String, Object> details;

}
