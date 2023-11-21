package com.pingpong.jlab.pingpong.global.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/*
 * 로그아웃 성공 핸들러
 * 로그아웃 성공 시 로그 남기고 홈페이지로 redirect
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{




    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication)
            throws IOException, ServletException {
        response.sendRedirect("/login");
    
    }
    
}
