package com.pingpong.jlab.pingpong.domain.record.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.pingpong.jlab.pingpong.domain.user.entity.User;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordseq;
    
    @Column(length = 100, nullable = false)
    private String recordname;

    @ManyToOne
    private User user;
    
}
