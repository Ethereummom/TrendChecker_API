package com.pingpong.jlab.pingpong.global.jwt.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingpong.jlab.pingpong.global.error.ErrorResponse;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
        AuthenticationException authException) throws IOException{
        
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter()
            .write(objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED)));
    }
    
}
