package com.pingpong.jlab.pingpong.domain.user.dto;

import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String userid;
    private String email;
    private String nickname;
    private String issocial;
    private int score;
    private int accounts;

    

    public User dtoToEntity(UserDto dto){
    
        User user = new User();
        user.setUserid(dto.getUserid());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setIssocial(dto.getIssocial());
        user.setScore(dto.getScore());
        user.setAccounts(dto.getAccounts());

        return user;

    }
}