package com.pingpong.jlab.pingpong.global.security.oauth2.handler;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pingpong.jlab.pingpong.global.cookie.CookieUtils;
import com.pingpong.jlab.pingpong.global.jwt.JwtTokenProvider;

import static com.pingpong.jlab.pingpong.global.security.oauth2.CookieOAuth2AuthorizationRequestRepository.*;

import com.pingpong.jlab.pingpong.global.security.oauth2.CustomOAuth2User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    private final JwtTokenProvider tokenProvider;

    @Value("${jwt.expire-seconds.access-token}")
	long accessTokenExpireSeconds;
	
	@Value("${jwt.expire-seconds.refresh-token}")
	long refreshTokenExpireSeconds;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication) throws IOException{
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if(oAuth2User.getRole().equals("temp")){
                String accessToken = tokenProvider.createAccessToken(oAuth2User.getUserid(), oAuth2User.getRole(), oAuth2User.getProfileimage());
                String refreshToken = tokenProvider.createRefreshToken();

                setAccessTokenInCookie(response, accessToken);
                setRefreshTokenInCookie(response, refreshToken);

                tokenProvider.updateRefreshToken(oAuth2User.getUserid(), refreshToken);
                response.sendRedirect(determineTargetUrl(request, response, authentication));
            }else{
                loginSuccess(response, oAuth2User);
                response.sendRedirect(determineTargetUrl(request, response, authentication));
            }
        }

    
    private void setAccessTokenInCookie(HttpServletResponse response, String accessToken){
        ResponseCookie token = ResponseCookie.from("accessTokenCookie", accessToken)
            .path(getDefaultTargetUrl())
            .sameSite("None")
            .secure(true)
            .maxAge(accessTokenExpireSeconds)
            .build();
        
        response.addHeader("Set-Cookie", token.toString());
    }

    private void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken){
        ResponseCookie token = ResponseCookie.from("refreshTokenCookie", refreshToken)
            .path(getDefaultTargetUrl())
            .sameSite("None")
            .secure(true)
            .maxAge(refreshTokenExpireSeconds)
            .build();

        response.addHeader("Set-Cookie", token.toString());
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException{

        String accessToken = tokenProvider.createAccessToken(oAuth2User.getUserid(), oAuth2User.getRole(), oAuth2User.getProfileimage());
        String refreshToken = tokenProvider.createRefreshToken();

        setAccessTokenInCookie(response, accessToken);
        setRefreshTokenInCookie(response, refreshToken);

        tokenProvider.updateRefreshToken(oAuth2User.getUserid(), refreshToken);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response
        ,Authentication authentication){
            Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
            String targetUrl;

            if(authentication.getAuthorities().toString().equals("ROLE_TEMP")){
                targetUrl = redirectUri.orElse(getCustomDefaultTargetUrl());
            } else {
                targetUrl = redirectUri.orElse(getCustomDefaultTargetUrl2());
            }

            return targetUrl;
        }

    protected String getCustomDefaultTargetUrl(){
        return "http://localhost:3000/signUp";
    }

    protected String getCustomDefaultTargetUrl2(){
        return "http://localhost:3000/main";
    }
    
}
