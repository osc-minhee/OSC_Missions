package com.boardapp.api.board.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.boardapp.api.board.domain.Board;
import com.boardapp.api.board.dto.BoardListResponse;
import com.boardapp.api.board.dto.BoardRequest;
import com.boardapp.api.board.dto.BoardResponse;
import com.boardapp.api.board.repository.BoardRepository;
import com.boardapp.api.global.exception.CustomException;
import com.boardapp.api.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 새로운 게시판 항목 생성
    public BoardResponse createBoard(BoardRequest request) {
        Board board = new Board(request.title(), request.description());

        return BoardResponse.from(boardRepository.save(board));
    }

    // 게시판 항목 조회
    public BoardResponse getBoardById(Long id) {
        return BoardResponse.from(findBoardOrThrow(id));
    }

    // 모든 게시판 항목 조회 (페이지네이션)
    public Page<BoardListResponse> getAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        long total = boards.getTotalElements();
        long offset = pageable.getOffset();
        List<Board> content = boards.getContent();

        List<BoardListResponse> responses = IntStream.range(0, content.size())
                .mapToObj(i -> BoardListResponse.from(content.get(i), total - offset - i))
                .toList();

        return new PageImpl<>(responses, pageable, total);
    }

    // 게시판 항목 수정
    public BoardResponse updateBoard(Long id, BoardRequest request) {
        Board existing = findBoardOrThrow(id);

        existing.setTitle(request.title());
        existing.setDescription(request.description());

        return BoardResponse.from(boardRepository.save(existing));
    }

    // 게시판 항목 삭제
    public void deleteBoard(Long id) {
        findBoardOrThrow(id);

        boardRepository.deleteById(id);
    }

    private Board findBoardOrThrow(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }
}
