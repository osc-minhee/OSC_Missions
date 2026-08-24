package com.boardapp.api.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.boardapp.api.global.entity.BaseEntity;

import lombok.Getter;

@Entity
@Table(name = "boards")
@Getter
public class Board extends BaseEntity {

    private String title;

    private String content;

    protected Board() {
    }

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
