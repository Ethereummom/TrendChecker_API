package com.pingpong.jlab.pingpong.domain.user.controller;

import com.pingpong.jlab.pingpong.domain.follow.dto.FollowRequestDto;
import com.pingpong.jlab.pingpong.domain.follow.service.FollowService;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.pingpong.jlab.pingpong.domain.user.dto.UserDto;
import com.pingpong.jlab.pingpong.domain.user.dto.UserUpdateDto;
import com.pingpong.jlab.pingpong.domain.user.service.UserService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;

import lombok.extern.log4j.Log4j2;

@Tag(name="User Controller" , description ="유저 데이터 관련 API")
@Log4j2
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @GetMapping(value = "")
    public ApiResponse getUserList(PaginationRequestDto paginationRequestDto){
        return userService.getAllUserList(paginationRequestDto);
    }

    @PostMapping(value = "")
    public ApiResponse addUser(@RequestBody UserDto user){
        return userService.addUser(user);
    }

    @DeleteMapping(value = "/{userseq}")
    public ApiResponse deleteUser(@PathVariable("userseq") Long userseq){
        return userService.deleteUser(userseq);
    } 

    @PutMapping(value = "")
    public ApiResponse updateUser(@RequestBody UserUpdateDto user){
        return userService.updateUser(user);
    }

    @GetMapping(value = "/{userseq}")
    public ApiResponse getUserDetails(@PathVariable("userseq") Long userseq){
        return userService.getUserDetails(userseq);
    }

    @GetMapping(value = "/admin")
    public ApiResponse getAdminList(@AuthenticationPrincipal UserDetails userDetails){
        return userService.getAdminList(userDetails);
    }

    @PostMapping(value = "/admin")
    public ApiResponse addAdmin(@RequestBody UserDto userinfo){
        log.info("입력 데이터 정보 ---- : : : " + userinfo);
        return userService.addUser(userinfo);
    }

    @GetMapping("/follow")
    public ApiResponse getUserFollowerList(PaginationRequestDto dto, @AuthenticationPrincipal JwtAuthentication userInfo){
        return followService.getUserFollowerList(dto, userInfo.getUserid());

    }

    @PostMapping(value = "/follow")
    public ApiResponse followUser(@RequestBody FollowRequestDto dto , @AuthenticationPrincipal JwtAuthentication authentication){
        return userService.followUser(dto,authentication.getUserid());
    }

}
