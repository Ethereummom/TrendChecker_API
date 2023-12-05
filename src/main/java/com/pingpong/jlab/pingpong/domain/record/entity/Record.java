package com.pingpong.jlab.pingpong.domain.record.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordseq;
    
    @Column(nullable = false)
    private int startprice;

    @Column(nullable = false)
    private int currentprice;

    @Column(nullable = false)
    private double percentage;

    @ManyToOne
    private User user;

    @ManyToOne
    private Asset asset;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime finishedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}