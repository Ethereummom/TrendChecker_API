package com.pingpong.jlab.pingpong.domain.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pingpong.jlab.pingpong.domain.record.dto.RecordRequestDto;
import com.pingpong.jlab.pingpong.domain.record.service.RecordService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/record")
public class RecordController {

    @Autowired
    RecordService recordService;


    @GetMapping("")
    public ApiResponse getUserRecord(@AuthenticationPrincipal JwtAuthentication userinfo){
        return recordService.getUserRecord(userinfo.getUserid());
    }


    @PostMapping("")
    public ApiResponse addUserRecord(@RequestBody RecordRequestDto recordInfo , 
                                     @AuthenticationPrincipal JwtAuthentication userinfo){
        
        log.info("Jwt Authentication Test ::::: -- " + userinfo);
        return recordService.addUserRecord(recordInfo, userinfo.getUserid());
    }

    @PutMapping("")
    public ApiResponse modifyUserRecord(@RequestBody Object a){
        return null;
    }

    @DeleteMapping("")
    public ApiResponse deleteUserRecord(@RequestBody Object a){
        return null;
    }
}
