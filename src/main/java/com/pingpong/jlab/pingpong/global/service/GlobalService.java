package com.pingpong.jlab.pingpong.global.service;

import com.pingpong.jlab.pingpong.domain.asset.repository.AssetRepository;
import com.pingpong.jlab.pingpong.domain.post.repository.PostRepository;
import com.pingpong.jlab.pingpong.domain.strategy.repository.StrategyRepository;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GlobalService {

    @Autowired
    StrategyRepository strategyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AssetRepository assetRepository;
    public ApiResponse getMainDataList(){

        Map<String, Object> mainDataList = new HashMap<>();

        mainDataList.put("userRankList",null);
        mainDataList.put("strategyRankList",null);
        mainDataList.put("topPostList" , null);

        return ApiResponse.res(200,"메인 화면 데이터리스트",mainDataList);
    }
}
