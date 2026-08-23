package com.boardapp.api.board.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardapp.api.board.dto.BoardListResponse;
import com.boardapp.api.board.dto.BoardRequest;
import com.boardapp.api.board.dto.BoardResponse;
import com.boardapp.api.board.service.BoardService;
import com.boardapp.api.global.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController implements BoardControllerDocs {

    private final BoardService boardService;

    @Override
    @GetMapping("/list")
    public ResponseEntity<Page<BoardListResponse>> getBoardList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardListResponse> boards = boardService.getAllBoards(pageable);
        return ResponseEntity.ok(boards);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        BoardResponse board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @Override
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        BoardResponse created = boardService.createBoard(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @Valid @RequestBody BoardRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        BoardResponse updated = boardService.updateBoard(id, request, currentUser);
        return ResponseEntity.ok(updated);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        boardService.deleteBoard(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
