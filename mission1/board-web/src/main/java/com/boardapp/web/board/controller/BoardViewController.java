package com.boardapp.web.board.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boardapp.web.board.client.BoardApiClient;
import com.boardapp.web.board.dto.BoardFormRequest;
import com.boardapp.web.board.dto.BoardListResponse;
import com.boardapp.web.board.dto.BoardResponse;
import com.boardapp.web.board.dto.PageResponse;
import com.boardapp.web.global.exception.ApiErrorMessageExtractor;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("api/v1/boards")
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardApiClient boardApiClient;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, Model model) {
        PageResponse<BoardListResponse> boards = boardApiClient.getBoards(page, 10);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        BoardResponse board = boardApiClient.getBoard(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new BoardFormRequest(null, null));
        model.addAttribute("mode", "create");
        return "board/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") BoardFormRequest form, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "create");
            return "board/form";
        }

        try {
            BoardResponse created = boardApiClient.createBoard(form);
            return "redirect:/boards/" + created.id();
        } catch (RestClientResponseException e) {
            model.addAttribute("mode", "create");
            model.addAttribute("apiError", ApiErrorMessageExtractor.extract(e));
            return "board/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        BoardResponse existing = boardApiClient.getBoard(id);
        model.addAttribute("form", new BoardFormRequest(existing.title(), existing.description()));
        model.addAttribute("mode", "edit");
        model.addAttribute("boardId", id);
        return "board/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("form") BoardFormRequest form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "edit");
            model.addAttribute("boardId", id);
            return "board/form";
        }

        try {
            boardApiClient.updateBoard(id, form);
            return "redirect:/boards/" + id;
        } catch (RestClientResponseException e) {
            model.addAttribute("mode", "edit");
            model.addAttribute("boardId", id);
            model.addAttribute("apiError", ApiErrorMessageExtractor.extract(e));
            return "board/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boardApiClient.deleteBoard(id);
            redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
            return "redirect:/boards";
        } catch (RestClientResponseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", ApiErrorMessageExtractor.extract(e));
            return "redirect:/boards/" + id;
        }
    }
}
