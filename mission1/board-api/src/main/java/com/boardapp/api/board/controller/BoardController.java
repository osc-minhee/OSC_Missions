package com.boardapp.api.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardapp.api.board.domain.Board;
import com.boardapp.api.board.dto.BoardListResponse;
import com.boardapp.api.board.dto.BoardResponse;
import com.boardapp.api.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 리스트 조회 (페이지네이션)
    @GetMapping("/list")
    public ResponseEntity<Page<BoardListResponse>> getBoardList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardListResponse> boards = boardService.getAllBoards(pageable);
        return ResponseEntity.ok(boards);
    }

    // 게시판 항목 조회 
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        BoardResponse board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    // 게시판 항목 생성
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody Board board) {
        BoardResponse created = boardService.createBoard(board);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 게시판 항목 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @RequestBody Board board) {
        BoardResponse updated = boardService.updateBoard(id, board);
        return ResponseEntity.ok(updated);
    }

    // 게시판 항목 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {    
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }   
}
