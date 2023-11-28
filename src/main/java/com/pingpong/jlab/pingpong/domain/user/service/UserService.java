package com.pingpong.jlab.pingpong.domain.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pingpong.jlab.pingpong.domain.user.dto.UserDto;
import com.pingpong.jlab.pingpong.domain.user.dto.UserResponseDto;
import com.pingpong.jlab.pingpong.domain.user.dto.UserUpdateDto;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import com.pingpong.jlab.pingpong.global.security.oauth2.Provider;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ApiResponse addUser(UserDto userinfo){

        if(userinfo != null){


            userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
            userinfo.setProvider(Provider.NONE);
            
            userRepository.save(userinfo.dtoToEntity(userinfo));
            ApiResponse res = new ApiResponse<>(200, "데이터 인서트 완료");
            return res;
        }
        else{
            ApiResponse res = new ApiResponse<>(500, "데이터 인서트 에러");
            return res;
        }
    }

    public ApiResponse getAllUserList(PaginationRequestDto dto){
        List<User> userList = userRepository.findAll();
        long totalCount = userRepository.count();
        PaginationResponseDto<UserResponseDto> userlist = new PaginationResponseDto(userList, totalCount,dto);
        return ApiResponse.res(200, "유저목록", userlist);
    }

    public ApiResponse getUserDetails(Long userseq){
        Optional<User> userDetail = userRepository.findById(userseq);

        if(userDetail.isPresent()){
            return ApiResponse.res(200, "유저상세", userDetail.get());
        }
        else{
            return ApiResponse.res(204, "해당 유저 정보가 존재하지 않습니다.");
        }

    }
    public ApiResponse updateUser(UserUpdateDto user){

        User userEntity = user.dtoToEntity(user);
        userRepository.save(userEntity);
        
        return ApiResponse.res(200, "유저정보 수정 완료");

    }

    public ApiResponse deleteUser(Long userseq){

        userRepository.deleteById(userseq);
        return ApiResponse.res(200, "유저 삭제 완료 !");

    }

    public ApiResponse getAdminList(UserDetails userDetails){

        if (userDetails.getAuthorities().equals("ADMIN")){
            List<User> adminlist = userRepository.findByRole("ADMIN");
            return ApiResponse.res(200,"관리자 리스트", adminlist);
        }
        else{
            return ApiResponse.res(204, "해당 유저는 관리자가 아닙니다.");
        }

    }
}
