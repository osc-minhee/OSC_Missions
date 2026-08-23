package com.boardapp.web.global.auth;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

public class SessionAuthorizationInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        // 1. 세션에 저장된 accessToken 꺼내기 (로그인 안했으면 null)
        String token = currentAccessToken();

        // 2. 토큰이 있을 경우, Authorization: Bearer {token} 헤더 부착 
        if (token != null) {
            request.getHeaders().setBearerAuth(token);
        }

        // 3. 헤더 세팅 여부와 무관하게 원래 요청은 그대로 진행 
        return execution.execute(request, body);
    }

    private String currentAccessToken() {
        // 1. 현재 스레드가 처리 중인 HTTP 요청 컨텍스트 확인 
        if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes)) {
            return null;
        }

        // 2. 세션이 없으면 새로 만들지 않고 null 리턴 
        HttpSession session = attributes.getRequest().getSession(false);
        if (session == null) {
            return null;
        }

        // 3. 세션에 저장된 사용자 정보에서 accessToken만 추출 
        SessionUser loginUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
        return loginUser == null ? null : loginUser.accessToken();
    }
}
