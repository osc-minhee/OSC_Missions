package com.boardapp.web.board.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.boardapp.web.board.dto.BoardApiRequest;
import com.boardapp.web.board.dto.BoardFormRequest;
import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;

@Component
public class BoardApiClient {

    private final RestClient boardApiRestClient;

    public BoardApiClient(RestClient boardApiRestClient) {
        this.boardApiRestClient = boardApiRestClient;
    }

    public PageResponse<BoardListResponse> getBoards(int page, int size) {
        return boardApiRestClient.get()
                .uri("/api/v1/boards/list?page={page}&size={size}", page, size)
                .retrieve()
                .body(new ParameterizedTypeReference<PageResponse<BoardListResponse>>() {
                });
    }

    public BoardResponse getBoard(Long id) {
        return boardApiRestClient.get()
                .uri("/api/v1/boards/{id}", id)
                .retrieve()
                .body(BoardResponse.class);
    }

    public BoardResponse createBoard(BoardFormRequest form) {
        return boardApiRestClient.post()
                .uri("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BoardApiRequest(form.title(), form.description()))
                .retrieve()
                .body(BoardResponse.class);
    }

    public BoardResponse updateBoard(Long id, BoardFormRequest form) {
        return boardApiRestClient.put()
                .uri("/api/v1/boards/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BoardApiRequest(form.title(), form.description()))
                .retrieve()
                .body(BoardResponse.class);
    }

    public void deleteBoard(Long id) {
        boardApiRestClient.delete()
                .uri("/api/v1/boards/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
