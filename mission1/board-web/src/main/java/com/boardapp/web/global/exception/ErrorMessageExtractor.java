package com.boardapp.web.global.exception;

import org.springframework.web.client.RestClientResponseException;

import com.boardapp.web.board.dto.ErrorResponse;

public final class ErrorMessageExtractor {

    private ErrorMessageExtractor() {
    }

    public static String extract(RestClientResponseException e) {
        try {
            ErrorResponse error = e.getResponseBodyAs(ErrorResponse.class);
            return error != null && error.message() != null ? error.message() : e.getMessage();
        } catch (Exception parseError) {
            return e.getMessage();
        }
    }
}
