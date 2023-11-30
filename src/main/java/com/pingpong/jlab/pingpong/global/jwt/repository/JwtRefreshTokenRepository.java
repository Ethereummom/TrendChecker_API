package com.pingpong.jlab.pingpong.global.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pingpong.jlab.pingpong.global.jwt.entity.RefreshToken;

public interface JwtRefreshTokenRepository extends JpaRepository<RefreshToken , String>{

    Optional<RefreshToken> findByUserid(String userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    
}
