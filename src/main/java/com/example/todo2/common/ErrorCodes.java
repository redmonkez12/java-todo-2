package com.example.todo2.common;

public enum ErrorCodes {
    BAD_CREDENTIALS("BAD_CREDENTIALS"),
    INVALID_ACTION("INVALID_ACTION"),
    BAD_PARAMETERS_OR_BODY("BAD_PARAMETERS_OR_BODY"),
    BAD_PARAMETER("BAD_PARAMETER"),
    BAD_PASSWORD_LENGTH("BAD_PASSWORD_LENGTH"),
    BAD_BODY("BAD_BODY"),
    WRONG_LABEL("WRONG_LABEL"),
    INVALID_EMAIL("INVALID_EMAIL"),
    FIRST_NAME_LENGTH("FIRST_NAME_LENGTH"),
    LAST_NAME_LENGTH("LAST_NAME_LENGTH"),

    USER_DUPLICATION("USER_DUPLICATION"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");

    private final String code;

    private ErrorCodes(String code) {
        this.code = code;
    }
}
