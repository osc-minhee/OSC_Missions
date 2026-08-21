package com.boardapp.web.board.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BoardListResponse(
        Long id,
        Long rowNumber,
        String title,
        String authorName,
        LocalDateTime createdAt) {
}
