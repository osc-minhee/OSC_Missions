package com.boardapp.web.board.client.feign;

import org.springframework.stereotype.Component;

import com.boardapp.web.board.client.BoardClient;
import com.boardapp.web.board.dto.BoardFormRequest;
import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardRequest;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeignBoardClient implements BoardClient {

    private final BoardFeignHttpClient boardFeignHttpClient;

    @Override
    public PageResponse<BoardListResponse> getBoards(int page, int size) {
        return boardFeignHttpClient.getBoards(page, size);
    }

    @Override
    public BoardResponse getBoard(Long id) {
        return boardFeignHttpClient.getBoard(id);
    }

    @Override
    public BoardResponse createBoard(BoardFormRequest form) {
        return boardFeignHttpClient.createBoard(new BoardRequest(form.title(), form.description()));
    }

    @Override
    public BoardResponse updateBoard(Long id, BoardFormRequest form) {
        return boardFeignHttpClient.updateBoard(id, new BoardRequest(form.title(), form.description()));
    }

    @Override
    public void deleteBoard(Long id) {
        boardFeignHttpClient.deleteBoard(id);
    }
}
