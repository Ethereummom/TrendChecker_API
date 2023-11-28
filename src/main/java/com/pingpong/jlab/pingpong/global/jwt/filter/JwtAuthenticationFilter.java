package com.pingpong.jlab.pingpong.global.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pingpong.jlab.pingpong.global.jwt.JwtAuthenticationToken;
import com.pingpong.jlab.pingpong.global.jwt.service.JwtTokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
        FilterChain filterChain) throws ServletException, IOException{

            String accessToken = tokenService.getAccessToken(request).orElse(null);

            if(accessToken != null){
                JwtAuthenticationToken authenticationToken = tokenService.getAuthenticationToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    
    @Override
    public boolean shouldNotFilter(HttpServletRequest request){
        return request.getRequestURI().equals("api/v1/token");
    }


}
