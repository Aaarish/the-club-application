package com.roya.the_club_application_backend.entities;

import com.roya.the_club_application_backend.dto.responses.PartResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "PARTS")
@Getter
public class Part {
    @Id
    private String partId;
    private String taskId;
    private String memberId;
    private LocalDateTime completedAt;
    private String note;

    public Part() {}

    public Part(String taskId, String memberId, String note) {
        this.partId = UUID.randomUUID().toString();
        this.taskId = taskId;
        this.memberId = memberId;
        this.completedAt = LocalDateTime.now();
        this.note = note;
    }

    public PartResponse toResponse() {
        return PartResponse.builder()
                .partId(this.getPartId())
                .taskId(this.getTaskId())
                .memberId(this.getMemberId())
                .completedAt(this.getCompletedAt())
                .note(this.getNote())
                .build();
    }
}
