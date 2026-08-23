package com.boardapp.api.auth.dto;

import com.boardapp.api.global.security.CustomUserDetails;

public record TokenResponse(
        Long id,
        String email,
        String nickname,
        String accessToken,
        String tokenType,
        long expiresInMillis) {

    public static TokenResponse of(CustomUserDetails userDetails, String accessToken, long expiresInMillis) {
        return new TokenResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getNickname(),
                accessToken,
                "Bearer",
                expiresInMillis);
    }
}
