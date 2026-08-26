package com.boardapp.web.board.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.boardapp.web.board.dto.BoardRequest;
import com.boardapp.web.board.dto.BoardFormRequest;
import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;
import com.boardapp.web.global.exception.BoardException;
import com.boardapp.web.global.exception.ErrorMessageExtractor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestBoardClient implements BoardClient {

    private final RestClient boardRestClient;

    @Override
    public PageResponse<BoardListResponse> getBoards(int page, int size) {
        try {
            return boardRestClient.get()
                    .uri("/api/v1/boards/list?page={page}&size={size}", page, size)
                    .retrieve()
                    .body(new ParameterizedTypeReference<PageResponse<BoardListResponse>>() {
                    });
        } catch (RestClientResponseException e) {
            throw toBoardException(e);
        }
    }

    @Override
    public BoardResponse getBoard(Long id) {
        try {
            return boardRestClient.get()
                    .uri("/api/v1/boards/{id}", id)
                    .retrieve()
                    .body(BoardResponse.class);
        } catch (RestClientResponseException e) {
            throw toBoardException(e);
        }
    }

    @Override
    public BoardResponse createBoard(BoardFormRequest form) {
        try {
            return boardRestClient.post()
                    .uri("/api/v1/boards")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new BoardRequest(form.title(), form.description()))
                    .retrieve()
                    .body(BoardResponse.class);
        } catch (RestClientResponseException e) {
            throw toBoardException(e);
        }
    }

    @Override
    public BoardResponse updateBoard(Long id, BoardFormRequest form) {
        try {
            return boardRestClient.put()
                    .uri("/api/v1/boards/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new BoardRequest(form.title(), form.description()))
                    .retrieve()
                    .body(BoardResponse.class);
        } catch (RestClientResponseException e) {
            throw toBoardException(e);
        }
    }

    @Override
    public void deleteBoard(Long id) {
        try {
            boardRestClient.delete()
                    .uri("/api/v1/boards/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw toBoardException(e);
        }
    }

    private BoardException toBoardException(RestClientResponseException e) {
        return new BoardException(e.getStatusCode().value(), ErrorMessageExtractor.extract(e));
    }
}
