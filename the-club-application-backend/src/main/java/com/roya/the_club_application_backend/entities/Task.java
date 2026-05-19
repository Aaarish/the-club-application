package com.roya.the_club_application_backend.entities;

import com.roya.the_club_application_backend.dto.responses.TaskResponse;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "TASKS")
@Getter
public class Task {
    @Id
    private String taskId;
    private String roomId;
    private String description;
    private String taskCreatorId;

    private LocalDateTime createdAt;
    private LocalDateTime toBeCompletedAt;

    public Task() {}

    public Task(String description, String roomId, String taskCreatorId, LocalDateTime toBeCompletedAt) {
        this.taskId = UUID.randomUUID().toString();
        this.roomId = roomId;
        this.description = description;
        this.taskCreatorId = taskCreatorId;
        this.createdAt = LocalDateTime.now();
        this.toBeCompletedAt = toBeCompletedAt;
    }

    public void changeDesc(String desc) {
        this.description = desc;
    }

    public void changeExpiry(LocalDateTime toBeCompletedAt) {
        this.toBeCompletedAt = toBeCompletedAt;
    }

    public TaskResponse toResponse() {
        return TaskResponse.builder()
                .taskId(this.getTaskId())
                .roomId(this.getRoomId())
                .description(this.getDescription())
                .taskCreatorId(this.getTaskCreatorId())
                .createdAt(this.getCreatedAt())
                .toBeCompletedAt(this.getToBeCompletedAt())
                .build();
    }

}
