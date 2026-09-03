package com.boardapp.web.board.client;

import com.boardapp.web.board.dto.BoardFormRequest;
import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;

public interface BoardApiClient {

    PageResponse<BoardListResponse> getBoards(int page, int size);

    BoardResponse getBoard(Long id);

    BoardResponse createBoard(BoardFormRequest form);

    BoardResponse updateBoard(Long id, BoardFormRequest form);

    void deleteBoard(Long id);
}
