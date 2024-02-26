package com.pingpong.jlab.pingpong.domain.recommend.controller;

import com.pingpong.jlab.pingpong.domain.recommend.service.RecommendService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Recommend Controller" , description = "유저 추천 히스토리 출력 API")
@RequestMapping("/api/v1/recommends")
@RestController
public class RecommendController {
    @Autowired
    RecommendService recommendService;

    @GetMapping("")
    public ApiResponse getUserRecommendHistoryList(@AuthenticationPrincipal JwtAuthentication userInfo){
        if(userInfo == null){
            ApiResponse.res(403, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        return recommendService.getUserRecommendHistoryList(userInfo.getUserid());
    }
}
