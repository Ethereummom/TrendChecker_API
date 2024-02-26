package com.pingpong.jlab.pingpong.domain.recommend.service;

import com.pingpong.jlab.pingpong.domain.recommend.entity.Recommend;
import com.pingpong.jlab.pingpong.domain.recommend.repository.RecommendRepository;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendService {

    @Autowired
    RecommendRepository recommendRepository;
    @Autowired
    UserRepository userRepository;
    public ApiResponse getUserRecommendHistoryList(String userId){

        Optional<User> user= userRepository.findByUserid(userId);
        List<Recommend> recommends = recommendRepository.findRecommendByUser(user.get());
        return ApiResponse.res(200,"유저가 추천한 게시물 목록", recommends);

    }

}
