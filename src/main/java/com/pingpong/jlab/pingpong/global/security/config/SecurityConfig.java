package com.pingpong.jlab.pingpong.global.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pingpong.jlab.pingpong.global.security.handler.CustomLogoutSuccessHandler;
import com.pingpong.jlab.pingpong.global.security.oauth2.CustomOAuth2UserService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
		http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/*").permitAll()
                // .antMatchers("/css/**").permitAll()
                // .antMatchers("/img/**").permitAll()
                // .antMatchers("/js/**").permitAll()
                // .antMatchers("/login").permitAll()
                // .antMatchers("/admin/ghaslrtbvjrhksflwkmakeorfindaccounthomeniq").permitAll()
                // .antMatchers("/temp/**").hasAnyRole("SUPERADMIN","ADMIN","TEMP")
                // .antMatchers("/admin/list").hasRole("SUPERADMIN")
                // .antMatchers("/admin/disableloginlock").hasRole("SUPERADMIN")
                // .antMatchers("/admin/makeaccountactive").hasRole("SUPERADMIN")
                // .antMatchers("/admin/makeaccountinactive").hasRole("SUPERADMIN")
                // .antMatchers("/admin/delete").hasRole("SUPERADMIN")
                // .antMatchers("/log/**").hasRole("SUPERADMIN")
                // .antMatchers("/**").hasAnyRole("SUPERADMIN","ADMIN")
                // .anyRequest().authenticated()
            
            .and()
            .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and()
            .oauth2Login()
            .authorizationEndpoint().baseUri("/oauth2/authorization")
            .and()
            .redirectionEndpoint().baseUri("/login/oauth/code/**")
            .and()
            .userInfoEndpoint().userService(customOAuth2UserService);
            
            // .authorizationRequestRepository(cookieOAuth)
            

        
        return http.build();
    }
}
