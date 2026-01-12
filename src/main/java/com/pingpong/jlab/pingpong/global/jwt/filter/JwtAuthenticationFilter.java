package com.pingpong.jlab.pingpong.global.jwt.filter;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pingpong.jlab.pingpong.global.jwt.JwtAuthenticationToken;
import com.pingpong.jlab.pingpong.global.jwt.service.JwtTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
        FilterChain filterChain) throws ServletException, IOException{

          /* 임시 소스 ... 테스트위한 필터 disable 처리 */
            String path = request.getRequestURI();

            if (path.startsWith("/api/v1/test/")) {
              filterChain.doFilter(request, response);
              return;
            }

            String accessToken = tokenService.getAccessToken(request).orElse(null);

            if(accessToken != null){
                JwtAuthenticationToken authenticationToken = tokenService.getAuthenticationToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    
    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/api-docs/json",
                "/api-docs",
                "/api/u/v1/social-login",
                "/swagger-ui/",
                "/swagger-config",
                "/swagger.yaml",
                "/requestBodies",
                "/swagger-",
                "/error",
                "/api/v1/app/kakao/authorize",
                "/api/v1/token"
        };

        String path = request.getRequestURI();
        boolean shouldNotFilter = Arrays.stream(excludePath).anyMatch(path::startsWith);

        return shouldNotFilter;
    }



}
