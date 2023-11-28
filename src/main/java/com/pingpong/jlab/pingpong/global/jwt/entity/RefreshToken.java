package com.pingpong.jlab.pingpong.global.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String userid;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    
    public RefreshToken(String userid, String refreshToken){
        this.userid = userid;
        this.refreshToken = refreshToken;
    }

    public void update(String refreshToken){
        this.refreshToken = refreshToken;
    }
    
}
