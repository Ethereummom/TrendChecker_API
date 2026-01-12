package com.pingpong.jlab.pingpong.domain.post.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postseq;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int recommend;

    @ManyToOne
    private User user;

    @ManyToOne
    private Strategy strategy;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
