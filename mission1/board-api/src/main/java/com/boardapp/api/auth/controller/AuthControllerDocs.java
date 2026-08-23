package com.boardapp.api.auth.controller;

import org.springframework.http.ResponseEntity;

import com.boardapp.api.auth.dto.LoginRequest;
import com.boardapp.api.auth.dto.SignupRequest;
import com.boardapp.api.auth.dto.SignupResponse;
import com.boardapp.api.auth.dto.TokenResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "회원가입/로그인 API")
public interface AuthControllerDocs {

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "201", description = "가입 성공")
    @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일")
    ResponseEntity<SignupResponse> signup(SignupRequest request);

    @Operation(summary = "로그인", description = "성공 시 JWT Access Token과 사용자 정보를 함께 발급합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
    ResponseEntity<TokenResponse> login(LoginRequest request);
}
