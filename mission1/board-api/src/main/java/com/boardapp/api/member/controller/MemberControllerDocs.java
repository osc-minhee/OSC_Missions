package com.boardapp.api.member.controller;

import org.springframework.http.ResponseEntity;

import com.boardapp.api.member.dto.MemberLoginRequest;
import com.boardapp.api.member.dto.MemberResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member", description = "회원 로그인 API")
public interface MemberControllerDocs {

    @Operation(summary = "로그인", description = "이메일/비밀번호를 검증하고 회원 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
    ResponseEntity<MemberResponse> login(MemberLoginRequest request);
}
