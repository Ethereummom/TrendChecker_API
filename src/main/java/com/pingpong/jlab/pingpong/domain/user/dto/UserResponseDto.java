package com.pingpong.jlab.pingpong.domain.user.dto;

import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String userid;
    private String role;
    private String email;
    private String nickname;
    private String issocial;
    private int score;
    private int accounts;

    

    public User dtoToEntity(UserDto dto){
    
        User user = new User();
        user.setUserid(dto.getUserid());
        user.setRole(dto.getRole());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setIssocial(dto.getIssocial());
        user.setScore(dto.getScore());
        user.setAccounts(dto.getAccounts());

        return user;

    }
    @Builder
    public UserResponseDto(String userid, String role, String email, String nickname, String issocial, int score, int accounts){
        this.userid = userid;
        this.role = role;
        this.email = email;
        this.nickname = nickname;
        this.issocial = issocial;
        this.score = score;
        this.accounts = accounts;
    }
}