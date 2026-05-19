package com.roya.the_club_application_backend.auth;

import com.roya.the_club_application_backend.auth.dto.AuthResponse;
import com.roya.the_club_application_backend.auth.dto.LoginRequest;
import com.roya.the_club_application_backend.auth.dto.RegisterRequest;
import com.roya.the_club_application_backend.global.responses.AppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.roya.the_club_application_backend.global.OperationLevel.APPLICATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacadeService {
    private final AuthService authService;

    public AppResponse register(RegisterRequest request) {
        if (isPhoneNumberRegistered(request.getPhone())) {
            log.info("User with phone number {} is already registered", request.getPhone());
            throw new RuntimeException("user with given phone number is already registered");
//            TODO: error response to be sent from the global exception handler
        }

        AuthResponse registerResponse =
                authService.register(request.getPhone(), request.getEmail(), request.getUsername(), request.getPassword());

        return AppResponse.builder()
                .source(APPLICATION)
                .isSuccess(true)
                .response(registerResponse)
                .message("User registered successfully with phone number " + request.getPhone())
                .build();
    }

    public AppResponse login(LoginRequest request) {
        AuthResponse loginResponse = authService.login(request.getPhone(), request.getPassword());

        return AppResponse.builder()
                .source(APPLICATION)
                .isSuccess(true)
                .response(loginResponse)
                .message("User logged in successfully with phone number " + request.getPhone())
                .build();
    }

    private boolean isPhoneNumberRegistered(String phone) {
        // check database for existing user with the given phone number
//        return db.query("select * from users where phone_number = ?");
        return false;
    }

}
