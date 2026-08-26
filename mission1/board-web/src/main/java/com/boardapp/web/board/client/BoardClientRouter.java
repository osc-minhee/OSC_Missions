package com.boardapp.web.board.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.boardapp.web.board.client.feign.FeignBoardClient;
import com.boardapp.web.board.dto.BoardFormRequest;
import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;

/**
 * board-api.client-type 값(rest 기본값 | feign)에 따라 RestBoardClient / FeignBoardClient 중 하나로 위임한다.
 * 두 구현을 나란히 남겨 LOC/에러 처리 방식을 비교하기 위한 용도이며 비교가 끝나면 라우터를 걷어내고 한쪽 구현만 남기는 것을 전제로 한다.
 */
@Component
@Primary
public class BoardClientRouter implements BoardClient {

    private final BoardClient delegate;

    public BoardClientRouter(
            RestBoardClient restBoardClient,
            FeignBoardClient feignBoardClient,
            @Value("${board-api.client-type:rest}") String clientType) {
        this.delegate = "feign".equalsIgnoreCase(clientType) ? feignBoardClient : restBoardClient;
    }

    @Override
    public PageResponse<BoardListResponse> getBoards(int page, int size) {
        return delegate.getBoards(page, size);
    }

    @Override
    public BoardResponse getBoard(Long id) {
        return delegate.getBoard(id);
    }

    @Override
    public BoardResponse createBoard(BoardFormRequest form) {
        return delegate.createBoard(form);
    }

    @Override
    public BoardResponse updateBoard(Long id, BoardFormRequest form) {
        return delegate.updateBoard(id, form);
    }

    @Override
    public void deleteBoard(Long id) {
        delegate.deleteBoard(id);
    }
}
