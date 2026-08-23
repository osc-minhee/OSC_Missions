package com.boardapp.api.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON001", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON002", "허용되지 않은 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON003", "서버 내부 오류가 발생했습니다."),

    // Board
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD001", "게시글을 찾을 수 없습니다."),

    // Auth
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "AUTH001", "이미 사용 중인 이메일입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH002", "이메일 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH003", "인증이 필요합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH004", "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
