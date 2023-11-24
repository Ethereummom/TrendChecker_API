package com.pingpong.jlab.pingpong.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pingpong.jlab.pingpong.domain.user.dto.UserDto;
import com.pingpong.jlab.pingpong.domain.user.dto.UserUpdateDto;
import com.pingpong.jlab.pingpong.domain.user.service.UserService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
