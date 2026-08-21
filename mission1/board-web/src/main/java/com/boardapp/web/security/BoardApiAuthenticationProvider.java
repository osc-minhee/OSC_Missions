package com.boardapp.web.security;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.boardapp.web.board.client.BoardApiClient;
import com.boardapp.web.board.dto.MemberResponse;

// board-web은 자체 회원 DB가 없으므로, board-api의 로그인 검증 결과에 인증을 위임한다
@Component
public class BoardApiAuthenticationProvider implements AuthenticationProvider {

    private final BoardApiClient boardApiClient;

    public BoardApiAuthenticationProvider(BoardApiClient boardApiClient) {
        this.boardApiClient = boardApiClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        MemberResponse member;
        try {
            member = boardApiClient.login(email, password);
        } catch (RestClientResponseException e) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다.", e);
        }

        BoardUserPrincipal principal = new BoardUserPrincipal(member.id(), member.name(), member.role());
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.role()));
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
