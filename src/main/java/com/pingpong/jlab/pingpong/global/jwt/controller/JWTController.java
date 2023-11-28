package com.pingpong.jlab.pingpong.global.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class JWTController {

    @PostMapping(value = "" , headers = "Authorization-refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void>refreshTokens(@RequestHeader("Authorization-refresh")String refreshToken){

        return null;
    }
    
}
