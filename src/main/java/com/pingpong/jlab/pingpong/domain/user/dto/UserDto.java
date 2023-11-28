package com.pingpong.jlab.pingpong.domain.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.global.security.oauth2.Provider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String userid;
    private String password;
    private String email;
    private String nickname;
    private Provider provider;
    private String issocial;
    private int score;
    private String role;
    private int accounts;

    public UserDto(String userid, String password){

    }
    

    public User dtoToEntity(UserDto dto){
    
        User user = new User();
        user.setUserid(dto.getUserid());
        user.setNickname(dto.getNickname());
        user.setProvider(provider);
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setIssocial(dto.getIssocial());
        user.setScore(dto.getScore());
        user.setRole(dto.getRole());
        user.setAccounts(dto.getAccounts());

        return user;

    }
}
