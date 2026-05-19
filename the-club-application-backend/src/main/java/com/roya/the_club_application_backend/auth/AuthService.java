package com.roya.the_club_application_backend.auth;

import com.roya.the_club_application_backend.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse register(String phone, String email, String username, String password);

    AuthResponse login(String phone, String password);

}
