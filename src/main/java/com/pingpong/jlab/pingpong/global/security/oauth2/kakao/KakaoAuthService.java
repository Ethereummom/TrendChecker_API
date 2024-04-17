package com.pingpong.jlab.pingpong.global.security.oauth2.kakao;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.domain.user.service.UserService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.UserInfo;
import com.pingpong.jlab.pingpong.global.jwt.dto.TokenReIssueResponseDto;
import com.pingpong.jlab.pingpong.global.jwt.service.JwtTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class KakaoAuthService {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenService jwtTokenService;

    public ApiResponse appKakaoLogin(UserInfo userInfo){
        log.info("User Info from Flutter :::: " + userInfo.getUserId());
        Optional<User> kakaoUserInfo = userRepository.findByUserid(userInfo.getUserId());
        if(kakaoUserInfo.isPresent()){
            String AccessToken = jwtTokenService.reIssueAccessToken(kakaoUserInfo.get().getUserid(),
                    kakaoUserInfo.get().getRole(),kakaoUserInfo.get().getProfileimage());
            String RefreshToken = jwtTokenService.reIssueRefreshToken(kakaoUserInfo.get().getUserid());

            return ApiResponse.res(200,"인증 토큰 발급 완료" , new TokenReIssueResponseDto(AccessToken, RefreshToken));
        }
        else{
            return ApiResponse.res(204,"회원가입이 필요합니다.");
        }

    }
}
