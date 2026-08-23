package com.boardapp.api.auth.dto;

import com.boardapp.api.user.domain.User;

public record SignupResponse(
        Long id,
        String email,
        String nickname) {

    public static SignupResponse from(User user) {
        return new SignupResponse(user.getId(), user.getEmail(), user.getNickname());
    }
}
