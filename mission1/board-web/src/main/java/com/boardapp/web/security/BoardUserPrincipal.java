package com.boardapp.web.security;

public record BoardUserPrincipal(Long memberId, String name, String role) {
}
