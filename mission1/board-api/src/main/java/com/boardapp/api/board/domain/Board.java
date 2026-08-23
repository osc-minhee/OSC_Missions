package com.boardapp.api.board.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.boardapp.api.user.domain.User;

import lombok.Getter;

@Entity
@Table(name = "boards")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    private User author;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    protected Board() {
    }

    public Board(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public boolean isOwnedBy(Long userId) {
        return author.getId().equals(userId);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}