package com.pingpong.jlab.pingpong.global.jwt.dto;

import com.pingpong.jlab.pingpong.global.jwt.entity.RefreshToken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenCreateRequestDto {

    private String userid;
    private String refreshToken;

    @Builder
    public RefreshTokenCreateRequestDto(String userid , String refreshToken){
        this.userid = userid;
        this.refreshToken = refreshToken;
    }

    public RefreshToken toEntity(){
        return RefreshToken.builder()
            .userid(userid)
            .refreshToken(refreshToken)
            .build();
    }
    
}
