package com.boardapp.web.global.auth;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class SessionModelAttributeAdvice {

    @ModelAttribute(SessionConst.LOGIN_USER)
    public SessionUser loginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);
    }
}
