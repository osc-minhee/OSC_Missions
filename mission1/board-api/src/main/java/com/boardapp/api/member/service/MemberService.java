package com.boardapp.api.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boardapp.api.global.exception.CustomException;
import com.boardapp.api.global.exception.ErrorCode;
import com.boardapp.api.member.domain.Member;
import com.boardapp.api.member.dto.MemberLoginRequest;
import com.boardapp.api.member.dto.MemberResponse;
import com.boardapp.api.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_LOGIN_FAILED));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.MEMBER_LOGIN_FAILED);
        }

        return MemberResponse.from(member);
    }
}
