package com.roya.the_club_application_backend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserResponse {
    private String userId;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;

}
