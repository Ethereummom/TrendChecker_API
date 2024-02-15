package com.pingpong.jlab.pingpong.domain.strategy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strategySeq;

    @Column(length = 255 , nullable = false)
    private String title;

    @Column(length = 1024 , nullable = false)
    private String content;

    @Column(length = 255 , nullable = false)
    private double startvalue;

    @Column(nullable = false)
    private double calculatedYield;

    @Column(nullable = false)
    private Integer recommendations;

    @ManyToOne
    private Asset asset;

    @ManyToOne
    private User user;

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


    
}
