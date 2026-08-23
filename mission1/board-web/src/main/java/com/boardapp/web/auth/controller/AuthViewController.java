package com.boardapp.web.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boardapp.web.auth.client.AuthApiClient;
import com.boardapp.web.auth.dto.LoginRequest;
import com.boardapp.web.auth.dto.SignupRequest;
import com.boardapp.web.auth.dto.TokenResponse;
import com.boardapp.web.global.auth.SessionConst;
import com.boardapp.web.global.auth.SessionUser;
import com.boardapp.web.global.exception.ApiErrorMessageExtractor;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthViewController {

    private final AuthApiClient authApiClient;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("form", new SignupRequest(null, null, null));
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("form") SignupRequest form, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        try {
            authApiClient.signup(form);
        } catch (RestClientResponseException e) {
            model.addAttribute("apiError", ApiErrorMessageExtractor.extract(e));
            return "auth/signup";
        }

        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해주세요.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("form", new LoginRequest(null, null));
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("form") LoginRequest form, BindingResult bindingResult,
            Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            TokenResponse token = authApiClient.login(form);
            SessionUser sessionUser = new SessionUser(token.id(), token.email(), token.nickname(), token.accessToken());
            request.getSession().setAttribute(SessionConst.LOGIN_USER, sessionUser);
        } catch (RestClientResponseException e) {
            model.addAttribute("apiError", ApiErrorMessageExtractor.extract(e));
            return "auth/login";
        }

        return "redirect:/boards";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/boards";
    }
}
