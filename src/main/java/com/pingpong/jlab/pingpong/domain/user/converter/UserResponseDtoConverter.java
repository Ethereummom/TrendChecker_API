package com.pingpong.jlab.pingpong.domain.user.converter;

import com.pingpong.jlab.pingpong.domain.user.dto.UserResponseDto;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserResponseDtoConverter {

    private UserResponseDtoConverter(){}

    public static UserResponseDto convert(User user){
        return Optional.ofNullable(user)
                .map(usr -> userResponseDtoBuilder(user))
                .orElse(null);
    }

    public static List<UserResponseDto> convert(List<User> userList){
        if(userList.isEmpty()){
            return new ArrayList<>();
        }
        return userList.stream()
                .map(UserResponseDtoConverter::userResponseDtoBuilder)
                .collect(Collectors.toList());
    }

    private static UserResponseDto userResponseDtoBuilder(User user){

        return UserResponseDto.builder()
                .userid(user.getUserid())
                .role(user.getRole())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .issocial(user.getIssocial())
                .score(user.getScore())
                .accounts(user.getAccounts())
                .build();
    }

}
