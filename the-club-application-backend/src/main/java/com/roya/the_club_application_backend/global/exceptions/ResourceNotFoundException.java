package com.roya.the_club_application_backend.global.exceptions;

import com.roya.the_club_application_backend.global.OperationLevel;

public class ResourceNotFoundException extends Exception {
    private OperationLevel source;

    public ResourceNotFoundException(OperationLevel source, String message) {
        super(message);
        this.source = source;
    }

    public OperationLevel getSource() {
        return this.source;
    }

}
