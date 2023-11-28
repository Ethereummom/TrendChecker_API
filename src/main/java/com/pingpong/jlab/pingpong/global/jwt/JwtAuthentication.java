package com.pingpong.jlab.pingpong.global.jwt;

import com.pingpong.jlab.pingpong.global.jwt.exception.JWTInvalidException;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class JwtAuthentication {

    public final String accessToken;
    public final String userid;
    public final String role;

    public JwtAuthentication(String accessToken, String userid, String role){

        if(accessToken.isEmpty() || accessToken.isBlank()){
            throw new JWTInvalidException();
        }

        if(userid == null){
            throw new JWTInvalidException();
        }

        this.accessToken = accessToken;
        this.userid = userid;
        this.role = role;
    }
    
}
