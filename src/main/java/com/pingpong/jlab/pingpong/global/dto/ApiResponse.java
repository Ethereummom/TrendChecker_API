package com.pingpong.jlab.pingpong.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    
    private int responseCode;
    private String message;
    private T data;

    /* 
     * 데이터가 없고 
     * 결과값만 존재할 때
    */
    public ApiResponse(int responseCode, String message){
        this.responseCode = responseCode;
        this.message = message;
        this.data = null;
    }
    /*
     * 데이터도 존재
     * 결과 객체도 존재
     */

    public ApiResponse(int responseCode, String message, T data){
        this.responseCode = responseCode;
        this.message = message;
        this.data = data;
    }
}
