package com.example.demo.exception;

public enum ErrorCodes {
    USER_NOT_FOUND(12000),
    USER_NOT_VALID(12001),
    USER_ALREADY_EXISTS(12002),
    USER_CHANGE_PASSWORD_OBJECT_NOT_VALID(12003),
    VALIDATION_ERROR(12004),

    // Il peut y avoir d'autres codes d'erreur ici (par exemple : pour Sale, Product, Login, etc.).
    ;

    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
