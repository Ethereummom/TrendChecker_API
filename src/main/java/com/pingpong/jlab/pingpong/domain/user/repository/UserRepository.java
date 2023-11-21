package com.pingpong.jlab.pingpong.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pingpong.jlab.pingpong.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    public User getUserInfo();    
    
}
