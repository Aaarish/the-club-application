package com.roya.the_club_application_backend.global.exceptions;

import com.roya.the_club_application_backend.global.OperationLevel;

public class ActionDeniedException extends Exception {
    private OperationLevel source;

    public ActionDeniedException(OperationLevel source, String message) {
        super(message);
        this.source = source;
    }

}
