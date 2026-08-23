package com.boardapp.web.board.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BoardResponse(
        Long id,
        String title,
        String content,
        Long authorId,
        String authorNickname,
        LocalDateTime createdAt) {
}
