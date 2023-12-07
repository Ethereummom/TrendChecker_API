package com.pingpong.jlab.pingpong.domain.strategy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.strategy.repository.StrategyRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;

@Service
public class StrategyService {

    @Autowired
    StrategyRepository strategyRepository;

    public ApiResponse getStrategyTopFiveRank(){

        List<Strategy> strategyRank = strategyRepository.getStrategyListBycalculatedYield();
        
        if(!strategyRank.isEmpty() && !(strategyRank == null)){
            return ApiResponse.res(200, "수익률 TOP 5 RANK", strategyRank);
        }
        else{
            return ApiResponse.res(204, "해당 데이터 없음");
        }
    }
}
