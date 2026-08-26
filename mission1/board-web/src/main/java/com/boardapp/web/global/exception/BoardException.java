package com.boardapp.web.global.exception;

public class BoardException extends RuntimeException {

    private final int statusCode;

    public BoardException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
