package com.roya.the_club_application_backend.entities;

import com.roya.the_club_application_backend.dto.responses.ClubResponse;
import jakarta.persistence.*;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CLUBS")
@Getter
public class Club implements Persistable<String> {
    @Id
    private String clubId;
    private String name;
    private String logo;
    private String description;
    private String ownerId;
    private String defaultRoomId;
    private LocalDateTime createdAt;

    public Club() {
    }

    public Club(String name, String logo, String description) {
        this.clubId = UUID.randomUUID().toString();
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setDefaultRoomId(String defaultRoomId) {
        this.defaultRoomId = defaultRoomId;
    }


    public void changeName(String name) {
        this.name = name;
    }

    public void changeLogo(String logo) {
        this.logo = logo;
    }

    public void changeDesc(String desc) {
        this.description = desc;
    }

    public ClubResponse toResponse() {
        return ClubResponse.builder()
                .clubId(this.clubId)
                .name(this.name)
                .logo(this.logo)
                .description(this.description)
                .ownerId(this.ownerId)
                .createdAt(this.createdAt)
                .build();
    }

    @Transient
    private boolean isNew = true;

    @Override
    public @Nullable String getId() {
        return this.clubId;
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
