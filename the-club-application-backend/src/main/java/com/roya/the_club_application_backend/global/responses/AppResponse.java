package com.roya.the_club_application_backend.global.responses;

import com.roya.the_club_application_backend.global.OperationLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppResponse {
    private OperationLevel source;
    private Object response;
    private boolean isSuccess;
    private String message;

}
