package com.pingpong.jlab.pingpong.domain.user.dto;

import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateDto {

    private Long userseq;
    private String userid;
    private String password;
    private String email;
    private String nickname;
    private String issocial;
    private int score;
    private String role;
    private int accounts;

    public UserUpdateDto(String userid, String password){
        this.userid = userid;
        this.password = password;

    }
    

    public User dtoToEntity(UserUpdateDto dto){
    
        User user = new User();
        user.setUserseq(dto.getUserseq());
        user.setUserid(dto.getUserid());
        user.setNickname(dto.getNickname());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setIssocial(dto.getIssocial());
        user.setScore(dto.getScore());
        user.setRole(dto.getRole());
        user.setAccounts(dto.getAccounts());

        return user;

    }
}