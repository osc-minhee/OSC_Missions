package com.boardapp.web.board.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MemberResponse(Long id, String name, String email, String role) {
}
