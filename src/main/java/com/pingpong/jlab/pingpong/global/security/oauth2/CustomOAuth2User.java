package com.pingpong.jlab.pingpong.global.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

import lombok.Getter;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User{
	
	private String role;
	private String uid;
	private String profileimage;
	
	public CustomOAuth2User(Collection<? extends GrantedAuthority>authorites, Map<String,Object>attributes,
			String nameAttributeKey, String uid, String role, String profileimage) {
			super(authorites, attributes, nameAttributeKey);
			
			this.role = role;
			this.uid = uid;
			this.profileimage = profileimage;
	}
}
