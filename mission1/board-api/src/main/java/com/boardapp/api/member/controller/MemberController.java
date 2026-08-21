package com.boardapp.api.member.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardapp.api.member.dto.MemberLoginRequest;
import com.boardapp.api.member.dto.MemberResponse;
import com.boardapp.api.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@Valid @RequestBody MemberLoginRequest request) {
        return ResponseEntity.ok(memberService.login(request));
    }
}
