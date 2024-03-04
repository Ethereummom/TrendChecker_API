package com.pingpong.jlab.pingpong.domain.follow.service;

import com.pingpong.jlab.pingpong.domain.follow.entity.Follow;
import com.pingpong.jlab.pingpong.domain.follow.repository.FollowRepository;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class FollowService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;

    public ApiResponse followUser(User fromUser, User toUser){

        Optional<Follow> followOrNot = followRepository.findFollowByToUserAndFromUser(fromUser,toUser);
        if(followOrNot.isPresent()){
            return ApiResponse.res(400,"이미 팔로우중인 유저입니다.");
        }
        log.info("Fromuser :: " + fromUser.getUserid() + " || toUser :: " + toUser.getUserid());
        Follow followInfo = Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        followRepository.save(followInfo);
        return ApiResponse.res(200, "팔로우 되었습니다.");

    }

    @Transactional
    public ApiResponse unFollowUser(User fromUser, User toUser){

        Optional<Follow> followOrNot = followRepository.findFollowByToUserAndFromUser(toUser, fromUser);
        if(followOrNot.isEmpty()){
            return ApiResponse.res(400,"팔로우중인 유저가 아닙니다.");
        }
        followRepository.deleteByToUserAndFromUser(toUser,fromUser);
        return ApiResponse.res(200, "팔로우 취소 완료");
    }
}
