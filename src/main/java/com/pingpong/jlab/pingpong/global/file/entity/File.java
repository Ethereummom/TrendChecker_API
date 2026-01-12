package com.pingpong.jlab.pingpong.global.file.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSeq;

    @Column(length = 255 , nullable = false)
    private String origin_file_name;

    @Column(length = 255 , nullable = false)
    private String path;

    @Column(length = 100 , nullable = false)
    private String type;

    @Column(nullable = false)
    private Long ref_id;

    @Column(length = 255, nullable = false)
    private String ref_entity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
    
}
