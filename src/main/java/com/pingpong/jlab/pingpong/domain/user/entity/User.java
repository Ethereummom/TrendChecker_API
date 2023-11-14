package com.pingpong.jlab.pingpong.domain.user.entity;

import javax.persistence.Entity;

import com.pingpong.jlab.pingpong.domain.record.entity.Record;

@Entity
public class User {

    private String uid;
    private String password;
    private String score;
    private Record record;
    
}
