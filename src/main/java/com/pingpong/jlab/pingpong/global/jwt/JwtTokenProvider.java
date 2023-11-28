package com.pingpong.jlab.pingpong.global.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pingpong.jlab.pingpong.global.jwt.dto.RefreshTokenCreateRequestDto;
import com.pingpong.jlab.pingpong.global.jwt.exception.JWTExpiredException;
import com.pingpong.jlab.pingpong.global.jwt.exception.JWTInvalidException;
import com.pingpong.jlab.pingpong.global.jwt.repository.JwtRefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long accessTokenExpireSeconds;
    private final long refreshTokenExpireSeconds;
    private final JwtRefreshTokenRepository refreshTokenRepository;

    private final String bearerType = "Bearer";

    @Value("${jwt.header.access-token}")
    String accessTokenHeader;

    @Value("${jwt.header.refresh-token}")
    String refreshTokenHeader;

    //JWT Provider Constructor
    public JwtTokenProvider(
        @Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.expire-seconds.access-token}") long accessTokenExpireSeconds,
        @Value("${jwt.expire-seconds.refresh-token}") long refreshTokenExpireSeconds,
        JwtRefreshTokenRepository refreshTokenRepository){

            this.secretKey = secretKey;
            this.accessTokenExpireSeconds = accessTokenExpireSeconds;
            this.refreshTokenExpireSeconds = refreshTokenExpireSeconds;
            this.refreshTokenRepository = refreshTokenRepository;

        }

    //Generate Access Token
    public String createAccessToken(String userid , String role, String profile_image){

        Map<String,Object> claims = Map.of("userid", userid, "role", role, "profile_image" , profile_image);
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + accessTokenExpireSeconds * 1000L);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiredDate)
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .compact();

    }

    //Generate Refresh Token
    public String createRefreshToken(){
        
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + refreshTokenExpireSeconds * 1000L);
        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(expiredDate)
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .compact();
    }

    //Decode Token
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    //Extract Token
    public Optional<String> extractAccessToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader(accessTokenHeader))
            .filter(accessToken -> accessToken.startsWith(bearerType))
            .map(accessToken -> accessToken.replace(bearerType, ""));
    }
    
    //Update Refresh Token
    @Transactional
    public void updateRefreshToken(String userid, String refreshToken){
        refreshTokenRepository.findByUserId(userid)
            .ifPresentOrElse(token -> token.update(refreshToken), 
            () -> saveRefreshToken(userid,refreshToken));
    }

    //Save Refresh Token
    @Transactional
    public void saveRefreshToken(String userid, String refreshToken){
        RefreshTokenCreateRequestDto refreshTokenCreateRequestDto = RefreshTokenCreateRequestDto.builder()
            .userid(userid)
            .refreshToken(refreshToken)
            .build();
        refreshTokenRepository.save(refreshTokenCreateRequestDto.toEntity());
    }

    //Token Validate (유효화)
    public void validateToken(String token){
        try{
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
        }catch(ExpiredJwtException e){
            throw new JWTExpiredException();
        }catch(JwtException | IllegalArgumentException e){
            throw new JWTInvalidException();
        }
    }
    
}
