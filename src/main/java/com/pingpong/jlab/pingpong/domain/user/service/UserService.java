package com.pingpong.jlab.pingpong.domain.user.service;

import java.util.List;
import java.util.Optional;

import com.pingpong.jlab.pingpong.domain.follow.dto.FollowRequestDto;
import com.pingpong.jlab.pingpong.domain.follow.entity.Follow;
import com.pingpong.jlab.pingpong.domain.follow.repository.FollowRepository;
import com.pingpong.jlab.pingpong.domain.follow.service.FollowService;
import com.pingpong.jlab.pingpong.domain.user.converter.UserResponseDtoConverter;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
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
    @Autowired
    FollowRepository followRepository;
    @Autowired
    FollowService followService;


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
        PaginationResponseDto<UserResponseDto> userlist = new PaginationResponseDto(UserResponseDtoConverter.convert(userList), totalCount,dto);
        return ApiResponse.res(200, "유저목록", userlist);
    }

    public ApiResponse getUserDetails(Long userseq){
        Optional<User> userDetail = userRepository.findById(userseq);

        if(userDetail.isPresent()){
            return ApiResponse.res(200, "유저상세", UserResponseDtoConverter.convert(userDetail.get()));
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
            return ApiResponse.res(200,"관리자 리스트", UserResponseDtoConverter.convert(adminlist));
        }
        else{
            return ApiResponse.res(204, "해당 유저는 관리자가 아닙니다.");
        }

    }

    public ApiResponse followUser(FollowRequestDto dto, String userId){
        Optional<User> fromUser = userRepository.findByUserid(userId);
        Optional<User> toUser = userRepository.findByUserid(dto.getTargetUserId());
        if(fromUser.isEmpty() || toUser.isEmpty()){
            return ApiResponse.res(400, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        if(dto.getFollowOrUnfollow().equals("FOLLOW")){
            return followService.followUser(fromUser.get(), toUser.get());
        //** 이미 팔로우중인 유저인지 확인 */
        }
        else if(dto.getFollowOrUnfollow().equals("UNFOLLOW")){
            return followService.unFollowUser(fromUser.get(), toUser.get());
        }
        return ApiResponse.res(400,ErrorCode.INVALID_INPUT_VALUE.getMessage());
    }
}
