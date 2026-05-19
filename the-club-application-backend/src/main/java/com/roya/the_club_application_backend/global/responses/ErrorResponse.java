package com.roya.the_club_application_backend.global.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String source;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;

}
