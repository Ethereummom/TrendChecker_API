package com.pingpong.jlab.pingpong.global.security.oauth2.handler;

import static com.pingpong.jlab.pingpong.global.security.oauth2.CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.pingpong.jlab.pingpong.global.cookie.CookieUtils;
import com.pingpong.jlab.pingpong.global.security.oauth2.CookieOAuth2AuthorizationRequestRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    private final CookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws java.io.IOException{
        
        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue)
            .orElse(("/"));
        
            targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", exception.getLocalizedMessage())
                    .build().toString();

            cookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequest(request , response);

            getRedirectStrategy().sendRedirect(request, response, targetUrl);


    }
}
