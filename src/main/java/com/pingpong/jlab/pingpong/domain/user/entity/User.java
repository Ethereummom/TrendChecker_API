package com.pingpong.jlab.pingpong.domain.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.pingpong.jlab.pingpong.domain.record.entity.Record;

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

    // @OneToMany(mappedBy = "record")
    // private List<Record> record = new ArrayList<>();

    @Column(length = 10, nullable = false)
    private String role;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int accounts;

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
