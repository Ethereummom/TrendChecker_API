package com.pingpong.jlab.pingpong.global.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pingpong.jlab.pingpong.global.jwt.filter.ExceptionHandlerFilter;
import com.pingpong.jlab.pingpong.global.jwt.filter.JwtAuthenticationEntryPoint;
import com.pingpong.jlab.pingpong.global.jwt.filter.JwtAuthenticationFilter;
import com.pingpong.jlab.pingpong.global.security.handler.CustomLogoutSuccessHandler;
import com.pingpong.jlab.pingpong.global.security.oauth2.CookieOAuth2AuthorizationRequestRepository;
import com.pingpong.jlab.pingpong.global.security.oauth2.CustomOAuth2UserService;
import com.pingpong.jlab.pingpong.global.security.oauth2.handler.OAuth2LoginFailureHandler;
import com.pingpong.jlab.pingpong.global.security.oauth2.handler.OAuth2LoginSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Autowired
    // private AuthenticationSuccessHandler authenticationSuccessHandler;

    // @Autowired
    // private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    // @Autowired
    // private AuthenticationSuccessHandler authenticationSuccessHandler;
    
    // @Autowired
    // private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    private CookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
            .csrf().disable()
            .authorizeHttpRequests()
                .antMatchers("/*").permitAll()

            
            // .and()
            // .formLogin()
            //     .usernameParameter("username")
            //     .passwordParameter("password")
            //     .successHandler(authenticationSuccessHandler)
            //     .failureHandler(authenticationFailureHandler)

                
            .and()
            .oauth2Login()
            .authorizationEndpoint().baseUri("/oauth2/authorization")
            .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
            .and()
            .redirectionEndpoint().baseUri("/login/oauth2/code/**")
            .and()
            .userInfoEndpoint().userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .addFilterBefore(jwtAuthenticationFilter,OAuth2AuthorizationRequestRedirectFilter.class)
            .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
            .build();
            
            // .authorizationRequestRepository(cookieOAuth)
    }
}
