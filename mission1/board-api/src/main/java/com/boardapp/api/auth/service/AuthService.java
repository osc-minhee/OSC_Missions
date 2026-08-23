package com.boardapp.api.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boardapp.api.auth.dto.LoginRequest;
import com.boardapp.api.auth.dto.SignupRequest;
import com.boardapp.api.auth.dto.SignupResponse;
import com.boardapp.api.auth.dto.TokenResponse;
import com.boardapp.api.global.exception.CustomException;
import com.boardapp.api.global.exception.ErrorCode;
import com.boardapp.api.global.security.CustomUserDetails;
import com.boardapp.api.global.security.jwt.JwtTokenProvider;
import com.boardapp.api.user.domain.Role;
import com.boardapp.api.user.domain.User;
import com.boardapp.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원 가입 
    public SignupResponse signup(SignupRequest request) {
        // 1. 이메일 중복 체크 여부 
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 2. 비밀번호 암호화 후 User 생성
        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname(),
                Role.USER);
        
        return SignupResponse.from(userRepository.save(user));
    }

    // 로그인 
    public TokenResponse login(LoginRequest request) {
        // 1. 이메일로 사용자 조회 
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        // 2. 비밀번호 일치 검증
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        // 3. 인증 성공 시 JWT 발급 
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtTokenProvider.generateToken(userDetails);
        return TokenResponse.of(userDetails, token, jwtTokenProvider.getExpirationMillis());
    }
}
