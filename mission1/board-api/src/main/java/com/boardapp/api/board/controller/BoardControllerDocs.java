package com.boardapp.api.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.boardapp.api.board.dto.BoardListResponse;
import com.boardapp.api.board.dto.BoardRequest;
import com.boardapp.api.board.dto.BoardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Board", description = "게시판 CRUD API")
public interface BoardControllerDocs {

    @Operation(summary = "게시글 목록 조회", description = "페이지네이션된 게시글 목록(10개 항목)을 최신순으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    ResponseEntity<Page<BoardListResponse>> getBoardList(Pageable pageable);

    @Operation(summary = "게시글 단건 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    ResponseEntity<BoardResponse> getBoard(Long id);

    @Operation(summary = "게시글 생성", description = "요청에 담긴 memberId를 작성자로 지정합니다.")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    ResponseEntity<BoardResponse> createBoard(BoardRequest request);

    @Operation(summary = "게시글 수정", description = "요청의 memberId가 작성자와 일치해야 수정할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @ApiResponse(responseCode = "403", description = "작성자가 아님")
    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    ResponseEntity<BoardResponse> updateBoard(Long id, BoardRequest request);

    @Operation(summary = "게시글 삭제", description = "memberId가 작성자와 일치해야 삭제할 수 있습니다.")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @ApiResponse(responseCode = "403", description = "작성자가 아님")
    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    ResponseEntity<Void> deleteBoard(Long id, Long memberId);
}
