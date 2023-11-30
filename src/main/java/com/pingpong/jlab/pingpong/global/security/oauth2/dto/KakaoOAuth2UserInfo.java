package com.pingpong.jlab.pingpong.global.security.oauth2.dto;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    public KakaoOAuth2UserInfo(Map<String , Object>attributes){
        super(attributes);
    }

    @Override
    public String getEmail(){
        return String.valueOf(((Map<String,Object>)attributes.get("kakao_account")).get("email"));
    }

    @Override
	public String getProfileImage() {
		return String.valueOf(((Map<String, Object>) ((Map<String, Object>) attributes.get("kakao_account")).get("profile")).get("profile_image_url"));
	}

    @Override
    public String getNickname(){
        return String.valueOf(((Map<String,Object>)attributes.get("kakao_account")).get("nickname"));
    
    }
}
