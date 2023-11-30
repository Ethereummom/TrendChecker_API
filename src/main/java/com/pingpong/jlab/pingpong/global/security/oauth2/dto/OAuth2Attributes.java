package com.pingpong.jlab.pingpong.global.security.oauth2.dto;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.global.security.oauth2.Provider;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Attributes {

    private String nameAttributeKey; //OAuth2 로그인 진행 시 키가 되는 필드 값, PK나 Sequence와 같은 의미.
    private OAuth2UserInfo oauth2UserInfo; // 소셜 타입 별 로그인 유저 정보(닉네임,이메일,프로필사진 등)

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Builder
    public OAuth2Attributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo){

        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuth2Attributes of(Provider provider, String userNameAttributeName, Map<String, Object> attributes){
        // if(provider == Provider.GOOGLE){
        //     return ofGoogle(userNameAttributeName, attributes)
        // }

        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){

        return OAuth2Attributes.builder()
            .nameAttributeKey(userNameAttributeName)
            .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
            .build();
    }

    public User toEntity(Provider provider, OAuth2UserInfo oAuth2UserInfo){
        
        User user = new User();
        user.setUserid(oauth2UserInfo.getEmail());
        user.setIssocial("Y");
        user.setNickname(oauth2UserInfo.getNickname());
        user.setEmail(oauth2UserInfo.getEmail());
        user.setPassword(passwordEncoder.encode(oauth2UserInfo.getEmail()));
        user.setProfileimage(oauth2UserInfo.getProfileImage());
        user.setProvider(provider);
        user.setRole("USER");
        return user;
            
    }
    
}
