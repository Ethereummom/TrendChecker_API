package com.pingpong.jlab.pingpong.domain.strategy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.asset.repository.AssetRepository;
import com.pingpong.jlab.pingpong.domain.strategy.dto.StrategyDTO;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.strategy.repository.StrategyRepository;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;

@Service
public class StrategyService {

    @Autowired
    StrategyRepository strategyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssetRepository assetRepository;
    

    public ApiResponse getStrategyTopFiveRank(){

        List<Strategy> strategyRank = strategyRepository.getStrategyListBycalculatedYield();
        
        if(!strategyRank.isEmpty() && !(strategyRank == null)){

            for(Strategy rankList : strategyRank){

    

            }

            return ApiResponse.res(200, "수익률 TOP 5 RANK", strategyRank);
        }
        else{
            return ApiResponse.res(204, "해당 데이터 없음");
        }
    }

    public ApiResponse addStrategy(StrategyDTO dto, String userinfo){

        Optional<User> user = userRepository.findByUserid(userinfo);
        dto.setUser(user.get());
        Optional<Asset> asset = assetRepository.findBySymbol(dto.getAssetType());
        dto.setAsset(asset.get());

        strategyRepository.save(dto.dtoToEntity(dto));
        return ApiResponse.res(200, "데이터 등록 완료");
    }

    public ApiResponse deleteStrategy(Long strategyseq){

        strategyRepository.deleteById(strategyseq);
        return ApiResponse.res(200, "전략 삭제 완료");


    }
}
