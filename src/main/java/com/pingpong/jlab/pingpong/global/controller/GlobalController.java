package com.pingpong.jlab.pingpong.global.controller;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.UserInfo;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Common Controller" , description = "공용 글로벌 API")
@RestController
@RequestMapping("/api/v1")
public class GlobalController {

    @Autowired
    UserRepository userRepository;

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
        return null;
    }

}
