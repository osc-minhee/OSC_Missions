package com.boardapp.api.global.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

// 클라이언트에게 내려주는 통일된 에러 응답 형태 
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        List<FieldErrorDetail> errors
) {

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(LocalDateTime.now(), errorCode.getStatus().value(), errorCode.getCode(),
                errorCode.getMessage(), List.of());
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(LocalDateTime.now(), errorCode.getStatus().value(), errorCode.getCode(),
                message, List.of());
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(LocalDateTime.now(), errorCode.getStatus().value(), errorCode.getCode(),
                errorCode.getMessage(), FieldErrorDetail.of(bindingResult));
    }

    public record FieldErrorDetail(String field, String value, String reason) {

        public static List<FieldErrorDetail> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(FieldErrorDetail::from)
                    .toList();
        }

        private static FieldErrorDetail from(FieldError fieldError) {
            String value = fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();
            return new FieldErrorDetail(fieldError.getField(), value, fieldError.getDefaultMessage());
        }
    }
}
