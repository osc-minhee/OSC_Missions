package com.boardapp.web.board.client.feign;

import java.io.InputStream;

import com.boardapp.web.board.dto.ErrorResponse;
import com.boardapp.web.global.exception.BoardException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class BoardFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = extractMessage(response);
        if (message == null) {
            message = defaultErrorDecoder.decode(methodKey, response).getMessage();
        }
        return new BoardException(response.status(), message);
    }

    private String extractMessage(Response response) {
        if (response.body() == null) {
            return null;
        }
        try (InputStream body = response.body().asInputStream()) {
            ErrorResponse error = objectMapper.readValue(body, ErrorResponse.class);
            return error != null ? error.message() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
