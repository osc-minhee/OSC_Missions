package com.boardapp.api.board.dto;

import java.time.LocalDateTime;

import com.boardapp.api.board.domain.Board;

public record BoardResponse(
        Long id,
        String title,
        String content,
        Long authorId,
        String authorNickname,
        LocalDateTime createdAt) {

    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getAuthor().getId(),
                board.getAuthor().getNickname(),
                board.getCreatedAt());
    }
}
