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

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

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
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            // 1. CSRF 및 기초 설정 (Lambda 방식)
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // 2. 권한 설정 (antMatchers -> requestMatchers)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login/**", "/oauth2/**", "/favicon.ico").permitAll()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )

            // 3. OAuth2 설정
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(auth -> auth
                    .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
                )
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/**")
                )
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
            )

            // 4. 예외 처리
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )

            // 5. 필터 순서 설정
            .addFilterBefore(jwtAuthenticationFilter, OAuth2AuthorizationRequestRedirectFilter.class)
            .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
}