package com.boardapp.web.board.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardRequest;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;

@FeignClient(name = "board-api", url = "${board-api.base-url}", configuration = FeignClientConfig.class)
public interface BoardFeignHttpClient {

    @GetMapping("/api/v1/boards/list")
    PageResponse<BoardListResponse> getBoards(@RequestParam("page") int page, @RequestParam("size") int size);

    @GetMapping("/api/v1/boards/{id}")
    BoardResponse getBoard(@PathVariable("id") Long id);

    @PostMapping("/api/v1/boards")
    BoardResponse createBoard(@RequestBody BoardRequest request);

    @PutMapping("/api/v1/boards/{id}")
    BoardResponse updateBoard(@PathVariable("id") Long id, @RequestBody BoardRequest request);

    @DeleteMapping("/api/v1/boards/{id}")
    void deleteBoard(@PathVariable("id") Long id);
}
