package com.pingpong.jlab.pingpong.domain.strategy.service;

import java.util.List;
import java.util.Optional;

import com.pingpong.jlab.pingpong.domain.asset.service.AssetService;
import com.pingpong.jlab.pingpong.global.calculator.YieldCalculator;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class StrategyService {

    @Autowired
    StrategyRepository strategyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AssetService assetService;
    

    public ApiResponse getStrategyTopFiveRank(PaginationRequestDto dto){
        if(dto == null){
            List<Strategy> strategyRank = strategyRepository.getStrategyListBycalculatedYield();

            if(!(strategyRank==null) && !strategyRank.isEmpty()){

                for(Strategy rankList : strategyRank){

                }
                return ApiResponse.res(200, "수익률 TOP 5 RANK", strategyRank);
            }
            else{
                return ApiResponse.res(204, "해당 데이터 없음");
            }

        }
        List<Strategy> strategyRank = strategyRepository.getStrategyListByRecommendationsAll();
        return ApiResponse.res(200, "전체 랭킹", strategyRank);
    }
    @Transactional
    public ApiResponse getStrategyList(PaginationRequestDto dto, Long strategyId){
        // 데이터 조회 전 자산 현재 가격 갱신
        ApiResponse res = assetService.updateAssetInfo(dto.getCategory());
        if(res.getResponseCode() != 200){
            return ApiResponse.res(500, res.getMessage());
        }

        // 자산 현재 가격 갱신 후 수익률 계산 후 업데이트
        Optional<Strategy> strategy = strategyRepository.findById(strategyId);
        Strategy sortedStrategy = strategy.get();

        Double startPrice = sortedStrategy.getStartvalue();
        Double currentPrice = sortedStrategy.getAsset().getCurrentprice();

        sortedStrategy.setCalculatedYield(YieldCalculator.calculatePercentageYield(startPrice, currentPrice));
        strategyRepository.save(sortedStrategy);


        PaginationResponseDto<Strategy> strategyInfo = strategyRepository.findStrategyByCategory(dto);

        return ApiResponse.res(200, "투자 전략 랭킹", strategyInfo);
    }
    @Transactional
    public ApiResponse addStrategy(StrategyDTO dto, String userinfo){

        Optional<User> user = userRepository.findByUserid(userinfo);
        if(user.isEmpty()) {
            return ApiResponse.res(500, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        dto.setUser(user.get());
        Optional<Asset> asset = assetRepository.findBySymbol(dto.getAssetType());

        if(asset.isEmpty()){
            return ApiResponse.res(500,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        dto.setAsset(asset.get());

        dto.setStartvalue(asset.get().getCurrentprice());
        strategyRepository.save(dto.dtoToEntity(dto));
        return ApiResponse.res(200, "데이터 등록 완료");
    }

    public ApiResponse deleteStrategy(Long strategyseq){

        strategyRepository.deleteById(strategyseq);
        return ApiResponse.res(200, "전략 삭제 완료");
    }
    public ApiResponse getSortedStrategy(Long strategyseq){
        Strategy toDaysStrategy = strategyRepository.getTodaysTopStrategy();
        return ApiResponse.res(200, "오늘의 최고 전략", toDaysStrategy);
    }
}
