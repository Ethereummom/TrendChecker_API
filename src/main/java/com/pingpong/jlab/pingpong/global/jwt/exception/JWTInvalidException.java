package com.pingpong.jlab.pingpong.global.jwt.exception;

import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;

public class JWTInvalidException extends JWTException{

    public JWTInvalidException(){
        super(ErrorCode.TOKEN_INVALID);
    }
    
}
