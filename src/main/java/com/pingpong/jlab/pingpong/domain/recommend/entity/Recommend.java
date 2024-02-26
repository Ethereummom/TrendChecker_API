package com.pingpong.jlab.pingpong.domain.recommend.entity;

import com.pingpong.jlab.pingpong.domain.post.entity.Post;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Table
@NoArgsConstructor
@Entity
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recSeq;

    @ManyToOne
    private User user;

    @ManyToOne
    private Strategy strategy;

    @ManyToOne
    private Post post;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public Recommend(User user, Strategy strategy){
        this.user = user;
        this.strategy = strategy;
    }

    @Builder
    public Recommend(User user, Post post){
        this.user = user;
        this.post = post;
    }
}
