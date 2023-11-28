package com.pingpong.jlab.pingpong.global.jwt.exception;

import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;

public class JWTExpiredException extends JWTException{

    public JWTExpiredException(){
        super(ErrorCode.TOKEN_EXPIRED);
    }
    
}
