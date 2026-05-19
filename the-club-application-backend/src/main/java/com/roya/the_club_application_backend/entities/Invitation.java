package com.roya.the_club_application_backend.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "INVITATIONS")
@Getter
public class Invitation {
    @Id
    private String invitationId;
    private String clubId;
    private String inviterId;
    private String message;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
    private LocalDateTime timestamp;
    private LocalDateTime expirationTime;

    private enum InvitationStatus {
        PENDING,
        ACCEPTED,
        DECLINED,
        EXPIRED
        ;
    }

    public Invitation() {}

    public Invitation(String clubId, String inviterId, String message) {
        this.invitationId = UUID.randomUUID().toString();
        this.clubId = clubId;
        this.inviterId = inviterId;
        this.message = message;
        this.status = InvitationStatus.PENDING;
        this.timestamp = LocalDateTime.now();
        this.expirationTime = timestamp.plusDays(7);

        // this meta-data to be encoded in the url that can be shared to the invitee,
        // so that when invitee clicks the url, backend can decode the url and get all the meta-data to process the invitation
    }

}
