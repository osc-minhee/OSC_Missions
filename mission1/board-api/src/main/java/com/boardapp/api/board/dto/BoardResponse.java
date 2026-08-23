package com.boardapp.api.board.dto;

import java.time.LocalDateTime;

import com.boardapp.api.board.domain.Board;

public record BoardResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt) {

    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getDescription(),
                board.getCreatedAt());
    }
}
