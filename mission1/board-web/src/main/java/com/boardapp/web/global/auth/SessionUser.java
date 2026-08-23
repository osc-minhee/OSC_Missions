package com.boardapp.web.global.auth;

import java.io.Serializable;

public record SessionUser(
        Long id,
        String email,
        String nickname,
        String accessToken) implements Serializable {
}
