package com.pingpong.jlab.pingpong.domain.strategy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pingpong.jlab.pingpong.domain.strategy.dto.StrategyDTO;
import com.pingpong.jlab.pingpong.domain.strategy.service.StrategyService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/strategy")
public class StrategyController {

    @Autowired
    StrategyService strategyService;

    @GetMapping(value="")
    public ApiResponse getStrategyList(PaginationRequestDto dto, @AuthenticationPrincipal JwtAuthentication userinfo){
        
        return null;
    }

    @GetMapping(value="/rank")
    public ApiResponse getStrategyTopFiveRank(@AuthenticationPrincipal JwtAuthentication userinfo){
        return strategyService.getStrategyTopFiveRank();

    }

    @PostMapping(value = "")
    public ApiResponse addStrategy(@RequestBody StrategyDTO dto, @AuthenticationPrincipal JwtAuthentication userinfo){
        
        return strategyService.addStrategy(dto, userinfo.getUserid());
    }

    @PutMapping(value = "")
    public ApiResponse modifyStrategy(@RequestBody StrategyDTO dto, @AuthenticationPrincipal JwtAuthentication userinfo){
        return null;
    }

    @DeleteMapping(value = "/{strategyseq}")
    public ApiResponse deleteStrategy(@PathVariable("strategyseq")Long strategyseq){
        return null;
    }
    
}
