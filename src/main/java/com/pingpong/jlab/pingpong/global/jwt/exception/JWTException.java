package com.pingpong.jlab.pingpong.global.jwt.exception;

import com.pingpong.jlab.pingpong.global.error.exception.BusinessException;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;

public class JWTException extends BusinessException{

    public JWTException(ErrorCode errorCode){
        super(errorCode);
    }
    
}
