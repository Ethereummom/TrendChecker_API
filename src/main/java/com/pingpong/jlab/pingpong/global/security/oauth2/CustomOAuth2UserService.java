package com.pingpong.jlab.pingpong.global.security.oauth2;

import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.pingpong.jlab.pingpong.global.security.oauth2.Provider;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.security.oauth2.dto.OAuth2Attributes;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public CustomOAuth2UserService(UserRepository userRepository, HttpSession httpSession){
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Provider provider = getProvider(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String,Object> attributes = oAuth2User.getAttributes();

        OAuth2Attributes extractAttributes = OAuth2Attributes.of(provider, userNameAttributeName, attributes);

        User user = getUser(provider, extractAttributes);

        return new CustomOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
            attributes,
            extractAttributes.getNameAttributeKey(),
            user.getUserid(),
            user.getRole(),
            user.getProfileimage());
    }

    private Provider getProvider(String registrationId){
        return Provider.KAKAO;
    }

    private User getUser(Provider provider, OAuth2Attributes attributes){

        log.info("Provider ------ :::" + provider + "||| OAuth2AttRibutes" + attributes );
        User findUser = userRepository.findByProviderAndUserid(provider,
        attributes.getOauth2UserInfo().getEmail()).orElse(null);

        if(findUser == null){
            return saveUser(provider, attributes);
        }

        return findUser;
    } 

    //카카오계정 회원가입처리
    private User saveUser(Provider provider, OAuth2Attributes attributes){
        User user = attributes.toEntity(provider, attributes.getOauth2UserInfo());
        
        return userRepository.save(user);
    }
    
}
