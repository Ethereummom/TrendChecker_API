package com.pingpong.jlab.pingpong.global.jwt.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.protobuf.Option;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.exception.UserNotFoundException;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.cookie.CookieUtils;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthenticationToken;
import com.pingpong.jlab.pingpong.global.jwt.JwtTokenProvider;
import com.pingpong.jlab.pingpong.global.jwt.dto.TokenReIssueResponseDto;
import com.pingpong.jlab.pingpong.global.jwt.entity.RefreshToken;
import com.pingpong.jlab.pingpong.global.jwt.exception.RefreshTokenCannotFoundException;
import com.pingpong.jlab.pingpong.global.jwt.repository.JwtRefreshTokenRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.expire-seconds.access-token}")
	int accessTokenExpireSeconds;
	@Value("${jwt.expire-seconds.refresh-token}")
	int refreshTokenExpireSeconds;

    @Transactional
    public TokenReIssueResponseDto reIssueTokens(String token){

        jwtTokenProvider.validateToken(token);

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
            .orElseThrow(() -> new RefreshTokenCannotFoundException());

        User user = userRepository.findByUserid(refreshToken.getUserid())
            .orElseThrow(() -> new UserNotFoundException());

        String reIssuedAccessToken = reIssueAccessToken(user.getUserid(), user.getRole(), user.getProfileimage());
        String reIssuedRefreshToken = reIssueRefreshToken(user.getUserid());

        String accessTokenCookie = createAccessTokenCookie(reIssuedAccessToken);
        String refreshTokenCookie = createRefreshTokenCookie(reIssuedRefreshToken);

        return new TokenReIssueResponseDto(accessTokenCookie, refreshTokenCookie);
    
    }

    //ReIssue(재발급) Access Token
    public String reIssueAccessToken(String userid, String role, String profile_image){
        return jwtTokenProvider.createAccessToken(userid, role, profile_image);
    }

    //ReIssue Refresh Token
    @Transactional
    public String reIssueRefreshToken(String userid){
        String token = jwtTokenProvider.createRefreshToken();
        RefreshToken reIssuedRefreshToken = new RefreshToken(userid, token);

        return refreshTokenRepository.save(reIssuedRefreshToken).getRefreshToken();
    }

    private String createAccessTokenCookie(String accessToken){
        return CookieUtils.addCookie("accessTokenCookie", accessToken, accessTokenExpireSeconds);
    }

    private String createRefreshTokenCookie(String refreshToken){
        return CookieUtils.addCookie("refreshTokenCookie", refreshToken, refreshTokenExpireSeconds);
    }

    public Optional<String> getAccessToken(HttpServletRequest request){
        String accessToken = jwtTokenProvider.extractAccessToken(request).orElse(null);

        if(accessToken != null){
            jwtTokenProvider.validateToken(accessToken);
        }
        return Optional.ofNullable(accessToken);
    }

    public JwtAuthenticationToken getAuthenticationToken(String accessToken){

        Claims claims = jwtTokenProvider.getClaims(accessToken);

        String userid = claims.get("userid" , String.class);
        String role = claims.get("role", String.class);

        JwtAuthentication principal = new JwtAuthentication(accessToken, userid, role);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new JwtAuthenticationToken(principal , null , authorities);
    }
    
}
