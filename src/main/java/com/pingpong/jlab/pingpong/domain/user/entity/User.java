package com.pingpong.jlab.pingpong.domain.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import com.pingpong.jlab.pingpong.domain.record.entity.Record;
import com.pingpong.jlab.pingpong.global.security.oauth2.Provider;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userseq;

    @Column(length = 30, nullable = false)
    private String userid;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(length = 1 ,nullable = false)
    private String issocial;

    @Column(nullable = false)
    private int score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    // @OneToMany(mappedBy = "record")
    // private List<Record> record = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int accounts;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    private String role;

    @Column(length = 500, nullable = true)
    private String profileimage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    /* 
    Insert시 동작 / 비영속 -> 영속 
    */
    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
    
}
