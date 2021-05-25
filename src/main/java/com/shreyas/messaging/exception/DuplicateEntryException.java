package com.shreyas.messaging.exception;

import org.springframework.stereotype.Component;

@Component
public class DuplicateEntryException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public DuplicateEntryException() {}

    public DuplicateEntryException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
