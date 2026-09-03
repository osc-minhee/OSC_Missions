package com.boardapp.api.board.validator;

import org.springframework.stereotype.Component;

import com.boardapp.api.board.domain.Board;
import com.boardapp.api.board.repository.BoardRepository;
import com.boardapp.api.global.exception.CustomException;
import com.boardapp.api.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardValidator {

    private final BoardRepository boardRepository;

    // 특정 ID에 맞는 게시판 항목이 존재하는지 검증 후 반환
    public Board getBoardOrThrow(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }
}
