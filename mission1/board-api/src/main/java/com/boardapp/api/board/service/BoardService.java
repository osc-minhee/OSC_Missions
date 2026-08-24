package com.boardapp.api.board.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boardapp.api.board.domain.Board;
import com.boardapp.api.board.dto.BoardListResponse;
import com.boardapp.api.board.dto.BoardRequest;
import com.boardapp.api.board.dto.BoardResponse;
import com.boardapp.api.board.repository.BoardRepository;
import com.boardapp.api.board.validator.BoardValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;

    // 새로운 게시판 항목 생성
    @Transactional
    public BoardResponse createBoard(BoardRequest request) {
        Board board = new Board(request.title(), request.content());
        return BoardResponse.from(boardRepository.save(board));
    }

    // 게시판 항목 조회
    public BoardResponse getBoardById(Long id) {
        return BoardResponse.from(boardValidator.getBoardOrThrow(id));
    }

    // 모든 게시판 항목 조회 (최신순 기준 표시 번호 포함)
    public Page<BoardListResponse> getAllBoards(Pageable pageable) {
        // 1. 모든 게시판 항목 조회 
        Page<Board> boards = boardRepository.findAll(pageable);

        // 2. 게시판 관련 메타데이터 변수로 저장
        long total = boards.getTotalElements();
        long offset = pageable.getOffset();
        List<Board> content = boards.getContent();

        // 3. rowNumber를 위해 등록순 id 대신 최신 글이 가장 큰 번호를 갖도록 표시 번호를 직접 계산
        List<BoardListResponse> responses = IntStream.range(0, content.size())
                .mapToObj(i -> BoardListResponse.from(content.get(i), total - offset - i))
                .toList();

        return new PageImpl<>(responses, pageable, total);
    }

    // 게시판 항목 수정
    @Transactional
    public BoardResponse updateBoard(Long id, BoardRequest request) {
        Board existing = boardValidator.getBoardOrThrow(id);

        existing.setTitle(request.title());
        existing.setContent(request.content());

        return BoardResponse.from(boardRepository.save(existing));
    }

    // 게시판 항목 삭제
    @Transactional
    public void deleteBoard(Long id) {
        boardValidator.getBoardOrThrow(id);
        boardRepository.deleteById(id);
    }
}
