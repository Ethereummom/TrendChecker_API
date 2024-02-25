package com.pingpong.jlab.pingpong.domain.subscribe.controller;

import com.pingpong.jlab.pingpong.domain.subscribe.service.SubscribeService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 투자전략 구독 관련 컨트롤러
 *
 * */
@Tag(name = "Subscribe Controller" , description = "구독 API")
@RequestMapping("/api/v1/subscriptions")
@RestController
public class SubscribeController {
    @Autowired
    SubscribeService subscribeService;

    @Tag(name = "User's Subscribe Strategy List" , description = "구독 목록 조회 API")
    @GetMapping("")
    public ApiResponse getUserActiveSubscribeList(@RequestBody PaginationRequestDto dto ,
                                                  @AuthenticationPrincipal JwtAuthentication userInfo){
        if(userInfo == null){
            return ApiResponse.res(403, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        return subscribeService.getSubscribeList(dto, userInfo.getUserid());
    }
}
