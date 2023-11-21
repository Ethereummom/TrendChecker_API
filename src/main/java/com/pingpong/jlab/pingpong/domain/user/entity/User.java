package com.pingpong.jlab.pingpong.domain.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.pingpong.jlab.pingpong.domain.record.entity.Record;

import lombok.Getter;

@Entity
@Table
@Getter
public class User {

    private Long user_seq;
    private String user_id;
    private String password;
    private String score;
    private Record record;
    private String role;
    private int accounts;

    private LocalDateTime createdAt;
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
