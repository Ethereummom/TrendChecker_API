package com.pingpong.jlab.pingpong.domain.user.exception;

import com.pingpong.jlab.pingpong.global.error.exception.BusinessException;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException{

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
    
}
