package com.pingpong.jlab.pingpong.global.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.security.service.userdetails.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        Optional<User> user = userRepository.findById(username);

        if(user.isPresent()){
            return new CustomUserDetails(user.get());
        }
        throw new UsernameNotFoundException("유효하지 않은 로그인 정보입니다.");
    }
    
}
