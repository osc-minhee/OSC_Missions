package com.boardapp.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boardapp.api.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}