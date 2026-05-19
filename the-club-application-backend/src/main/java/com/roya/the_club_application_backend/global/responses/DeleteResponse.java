package com.roya.the_club_application_backend.global.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteResponse {
    private String resourceId;
    private String message;
    private boolean isSuccess;

}
