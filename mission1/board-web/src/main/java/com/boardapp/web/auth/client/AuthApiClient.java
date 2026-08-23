package com.boardapp.web.auth.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.boardapp.web.auth.dto.LoginRequest;
import com.boardapp.web.auth.dto.SignupRequest;
import com.boardapp.web.auth.dto.TokenResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthApiClient {

    private final RestClient boardApiRestClient;

    public void signup(SignupRequest request) {
        boardApiRestClient.post()
                .uri("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    public TokenResponse login(LoginRequest request) {
        return boardApiRestClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(TokenResponse.class);
    }
}
