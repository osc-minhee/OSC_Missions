package com.boardapp.web.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenResponse(
        Long id,
        String email,
        String nickname,
        String accessToken,
        String tokenType,
        long expiresInMillis) {
}
