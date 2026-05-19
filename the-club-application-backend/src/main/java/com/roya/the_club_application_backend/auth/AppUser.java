package com.roya.the_club_application_backend.auth;

import com.roya.the_club_application_backend.dto.responses.AppUserResponse;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "APP_USERS")
@Getter
public class AppUser {
    @Id
    private String userId;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;

//    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
//    private List<RoomMember> members;

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public AppUser() {}

    public AppUser(String phoneNumber, String email, String username, String password) {
        this.userId = UUID.randomUUID().toString();
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public AppUserResponse toResponse() {
        return AppUserResponse.builder()
                .userId(userId)
                .email(email)
                .phoneNumber(phoneNumber)
                .username(username)
                .password(password)
                .build();
    }
}
