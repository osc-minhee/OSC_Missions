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
import com.boardapp.api.global.security.CustomUserDetails;
import com.boardapp.api.user.domain.User;
import com.boardapp.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 새로운 게시판 항목 생성
    public BoardResponse createBoard(BoardRequest request, CustomUserDetails currentUser) {
        User author = userRepository.getReferenceById(currentUser.getId());
        Board board = new Board(request.title(), request.content(), author);
        return BoardResponse.from(boardRepository.save(board));
    }

    // 게시판 항목 조회
    public BoardResponse getBoardById(Long id) {
        return BoardResponse.from(findBoardOrThrow(id));
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

    // 게시판 항목 수정 (작성자 본인 또는 관리자만 가능)
    public BoardResponse updateBoard(Long id, BoardRequest request, CustomUserDetails currentUser) {
        Board existing = findBoardOrThrow(id);
        validateOwner(existing, currentUser);

        existing.setTitle(request.title());
        existing.setContent(request.content());

        return BoardResponse.from(boardRepository.save(existing));
    }

    // 게시판 항목 삭제 (작성자 본인 또는 관리자만 가능)
    public void deleteBoard(Long id, CustomUserDetails currentUser) {
        Board existing = findBoardOrThrow(id);
        validateOwner(existing, currentUser);
        boardRepository.delete(existing);
    }

    // 특정 ID에 맞는 게시판 항목 직접 조회 메서드
    private Board findBoardOrThrow(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    private void validateOwner(Board board, CustomUserDetails currentUser) {
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ROLE_ADMIN));
        if (!isAdmin && !board.isOwnedBy(currentUser.getId())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }
}
