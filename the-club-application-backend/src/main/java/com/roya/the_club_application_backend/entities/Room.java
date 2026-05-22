package com.roya.the_club_application_backend.entities;

import com.roya.the_club_application_backend.dto.responses.RoomResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ROOMS")
@Getter
@Setter
public class Room implements Persistable<String> {
    @Id
    private String roomId;
    private String clubId;
    private String name;
    private String description;
    private String themeImage;
    private LocalDateTime createdAt;
    private int maxNumOfMissedTasksInRow;
    private int maxNumOfMissedTasksInTotal;

    public Room() {
    }

    public Room(String name, String description, String clubId) {
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.clubId = clubId;
        this.createdAt = LocalDateTime.now();
        this.maxNumOfMissedTasksInRow = 3;
        this.maxNumOfMissedTasksInTotal = 5;
    }

    public Room(Club club) {
        this.roomId = UUID.randomUUID().toString();
        this.name = club.getName();
        this.description = club.getDescription();
        this.clubId = club.getClubId();
        this.createdAt = LocalDateTime.now();
        this.maxNumOfMissedTasksInRow = 3;
        this.maxNumOfMissedTasksInTotal = 5;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDesc(String desc) {
        this.description = desc;
    }

    public void changeThemeImage(String themeImage) {
        this.themeImage = themeImage;
    }

    public void changeRoomPolicy(int missedTasksInRow, int missedTasksInTotal) {
        this.maxNumOfMissedTasksInRow = missedTasksInRow;
        this.maxNumOfMissedTasksInTotal = missedTasksInTotal;
    }

    public RoomResponse toResponse() {
        return RoomResponse.builder()
                .roomId(this.roomId)
                .name(this.name)
                .description(this.description)
                .themeImage(this.themeImage)
                .createdAt(this.createdAt)
                .clubId(this.clubId)
                .maxNumOfMissedTasksInRow(this.maxNumOfMissedTasksInRow)
                .maxNumOfMissedTasksInTotal(this.maxNumOfMissedTasksInTotal)
                .build();
    }

    @Transient
    private boolean isNew = true;

    @Override
    public @Nullable String getId() {
        return this.roomId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}
