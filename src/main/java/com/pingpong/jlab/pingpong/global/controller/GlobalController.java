package com.pingpong.jlab.pingpong.global.controller;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.UserInfo;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;
import com.pingpong.jlab.pingpong.global.service.GlobalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Common Controller" , description = "공용 글로벌 API")
@RestController
@RequestMapping("/api/v1")
public class GlobalController {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GlobalService globalService;

    @RequestMapping("/login")
    public String userLogin(){
        return "hello";
    }

    @PostMapping("/oauth/token")
    private ApiResponse getAuthentication(@RequestBody UserInfo userInfo){

        Optional<User> user = userRepository.findByUserid(userInfo.getUserId());
        if(user.isEmpty()){
            return ApiResponse.res(400, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        if(passwordEncoder.matches(userInfo.getPasswd(), user.get().getPassword())){
            return null;
        }
        return null;
    }

    @GetMapping("/main")
    public ApiResponse getMainDataList(@AuthenticationPrincipal JwtAuthentication authentication){
        return globalService.getMainDataList();
    }

}
