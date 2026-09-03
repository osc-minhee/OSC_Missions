package com.boardapp.api.board.dto;

import java.time.LocalDateTime;

import com.boardapp.api.board.domain.Board;

public record BoardListResponse(
        Long id,
        Long rowNumber,
        String title,
        LocalDateTime createdAt) {

    public static BoardListResponse from(Board board, long rowNumber) {
        return new BoardListResponse(
                board.getId(),
                rowNumber,
                board.getTitle(),
                board.getCreatedAt());
    }
}
