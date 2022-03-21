package com.softtech.market.enums;

public enum UserErrorMessage implements BaseErrorMessage{

    USER_NOT_FOUND("User not found!"),
    USERNAME_ALREADY_EXİSTS("Username already exists");

    private String message;
    UserErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
