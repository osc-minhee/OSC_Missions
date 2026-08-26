package com.boardapp.web.board.client;

import com.boardapp.web.board.dto.BoardFormRequest;
import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;

// 구현체는 board-api 오류 응답을 com.boardapp.web.global.exception.BoardException(unchecked)으로 통일해서 던진다.
public interface BoardClient {

    PageResponse<BoardListResponse> getBoards(int page, int size);

    BoardResponse getBoard(Long id);

    BoardResponse createBoard(BoardFormRequest form);

    BoardResponse updateBoard(Long id, BoardFormRequest form);

    void deleteBoard(Long id);
}
