package com.pingpong.jlab.pingpong.global.jwt.exception;

import com.pingpong.jlab.pingpong.global.error.exception.BusinessException;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;

public class RefreshTokenCannotFoundException extends BusinessException{

    public RefreshTokenCannotFoundException(){
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
    
}
