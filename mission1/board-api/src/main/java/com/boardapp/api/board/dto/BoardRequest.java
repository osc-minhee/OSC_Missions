package com.boardapp.api.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardRequest(
        @NotBlank(message = "제목을 입력해주세요.") String title,
        @NotBlank(message = "내용을 입력해주세요.") String description,
        @NotNull(message = "요청자 회원 ID가 필요합니다.") Long memberId) {
}
