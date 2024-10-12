package com.example.demo.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {

    @Getter
    private ErrorCodes errorCode;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable reason) {
        super(message, reason);
    }

    public EntityNotFoundException(String message, Throwable reason, ErrorCodes errorCode) {
        super(message, reason);
        this.errorCode = errorCode;
    }

    public EntityNotFoundException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
