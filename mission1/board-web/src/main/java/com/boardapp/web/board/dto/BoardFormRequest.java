package com.boardapp.web.board.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardFormRequest(
        @NotBlank(message = "제목을 입력해주세요.") String title,
        @NotBlank(message = "내용을 입력해주세요.") String description) {
}
