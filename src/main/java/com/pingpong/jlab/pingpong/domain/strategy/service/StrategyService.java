package com.pingpong.jlab.pingpong.domain.strategy.service;

import java.util.List;
import java.util.Optional;

import com.pingpong.jlab.pingpong.domain.asset.service.AssetService;
import com.pingpong.jlab.pingpong.domain.strategy.converter.StrategyDtoConverter;
import com.pingpong.jlab.pingpong.domain.subscribe.converter.SubscribeDtoConverter;
import com.pingpong.jlab.pingpong.domain.subscribe.entity.Subscribe;
import com.pingpong.jlab.pingpong.domain.subscribe.repository.SubscribeRepository;
import com.pingpong.jlab.pingpong.domain.subscribe.repository.SubscribeRepositoryCustomImpl;
import com.pingpong.jlab.pingpong.global.calculator.YieldCalculator;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
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

    @Autowired
    SubscribeRepository subscribeRepository;
    

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
    public ApiResponse getStrategyList(PaginationRequestDto dto, Long strategySeq){
        // 데이터 조회 전 자산 현재 가격 갱신
        ApiResponse res = assetService.updateAssetInfo(dto.getCategory());
        if(res.getResponseCode() != 200){
            return ApiResponse.res(500, res.getMessage());
        }

        // 자산 현재 가격 갱신 후 수익률 계산 후 업데이트
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);
        Strategy sortedStrategy = strategy.get();

        Double startPrice = sortedStrategy.getStartvalue();
        Double currentPrice = sortedStrategy.getAsset().getCurrentprice();

        sortedStrategy.setCalculatedYield(YieldCalculator.calculatePercentageYield(startPrice, currentPrice));
        strategyRepository.save(sortedStrategy);


        PaginationResponseDto<Strategy> strategyInfo = strategyRepository.findStrategyByCategory(dto);

        return ApiResponse.res(200, "투자 전략 랭킹", strategyInfo);
    }

    @Transactional
    public ApiResponse getStrategyDetail(Long strategySeq , String symbol){
        ApiResponse res = assetService.updateAssetInfo(symbol);
        log.info("data from realTime assets ::::" + res.getMessage());
        if(res.getResponseCode() != 200){
            return ApiResponse.res(500, res.getMessage());
        }

        // 자산 현재 가격 갱신 후 수익률 계산 후 업데이트
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);
        Strategy sortedStrategy = strategy.get();

        Double startPrice = sortedStrategy.getStartvalue();
        Double currentPrice = sortedStrategy.getAsset().getCurrentprice();

        sortedStrategy.setCalculatedYield(YieldCalculator.calculatePercentageYield(startPrice, currentPrice));
        log.info("수익률계산:::::: --------" + sortedStrategy.getCalculatedYield());
        strategyRepository.save(sortedStrategy);

        Optional<Strategy> strategyInfo = strategyRepository.findById(strategySeq);

        if(strategyInfo.isEmpty()){
            return ApiResponse.res(204,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        log.info("출력 전략 데이터 ::::::: ----------- " + strategyInfo.get().getUser().getNickname());
        return ApiResponse.res(200, "투자 전략 정보" ,StrategyDtoConverter.convert(strategyInfo.get()));
    }
    @Transactional
    public ApiResponse addStrategy(StrategyDTO dto, String userinfo){

        Optional<User> user = userRepository.findByUserid(userinfo);
        if(user.isEmpty()) {
            return ApiResponse.res(500, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        dto.setUser(user.get());
        // 데이터 조회 전 자산 현재 가격 갱신
        ApiResponse res = assetService.updateAssetInfo(dto.getAssetType());
        log.info("res from ASSET ::: " + res.getResponseCode());
        if(res.getResponseCode() != 200){
            return ApiResponse.res(500, res.getMessage());
        }
        Optional<Asset> asset = assetRepository.findBySymbol(dto.getAssetType());

        if(asset.isEmpty()){
            return ApiResponse.res(500,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        dto.setAsset(asset.get());

        dto.setStartvalue(asset.get().getCurrentprice());
        strategyRepository.save(dto.dtoToEntity(dto));
        return ApiResponse.res(200, "데이터 등록 완료");
    }

    public ApiResponse deleteStrategy(Long strategySeq){
        if(strategySeq==null){
            return ApiResponse.res(400,ErrorCode.MISSING_INPUT_VALUE.getMessage());
        }
        strategyRepository.deleteById(strategySeq);
        return ApiResponse.res(200, "전략 삭제 완료");
    }

    public ApiResponse finishStrategy(Long strategySeq){
        if(strategySeq==null){
            return ApiResponse.res(400,ErrorCode.MISSING_INPUT_VALUE.getMessage());
        }
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);
        if(strategy.isEmpty()){
            return ApiResponse.res(400,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        Strategy strategyInfo = strategy.get();
        strategyInfo.setEndYn("Y");
        strategyRepository.save(strategyInfo);
        return ApiResponse.res(200,"투자 전략 종료 완료");
    }
    public ApiResponse getSortedStrategy(Long strategySeq){
        Strategy toDaysStrategy = strategyRepository.getTodaysTopStrategy();
        return ApiResponse.res(200, "오늘의 최고 전략", toDaysStrategy);
    }

    public ApiResponse increaseRecommend(Long strategySeq){
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);
        Strategy strategyEntity = strategy.get();
        strategyEntity.addRecommend();
        strategyRepository.save(strategyEntity);
        return ApiResponse.res(200,"추천되었습니다");
    }
    public ApiResponse decreaseRecommend(Long strategySeq){
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);
        Strategy strategyEntity = strategy.get();
        strategyEntity.decreaseRecommend();
        strategyRepository.save(strategyEntity);
        return ApiResponse.res(200, "추천취소되었습니다");
    }
    @Transactional
    public ApiResponse subscribeStrategy(Long strategySeq, String userId){

        Optional<User> user = userRepository.findByUserid(userId);
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);
        if(user.isEmpty()){
            ApiResponse.res(400,ErrorCode.USER_NOT_FOUND.getMessage());
        }
        if(strategy.isEmpty()){
            ApiResponse.res(400,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        if(!(subscribeRepository.findSubscribeByUserAndStrategy(user.get(),strategy.get())).isEmpty()){
            return ApiResponse.res(400,ErrorCode.USER_ALREADY_SUBSCRIBED.getMessage());
        }
        Strategy strategyInfo = strategy.get();
        strategyInfo.addSubscriberCount();
        strategyRepository.save(strategyInfo);

        Subscribe subscribeInfo = Subscribe.builder()
                        .strategy(strategy.get())
                        .user(user.get())
                        .build();
        subscribeRepository.save(subscribeInfo);
        return ApiResponse.res(200,"구독 완료");
    }
    @Transactional
    public ApiResponse unSubscribeStrategy(Long strategySeq, String userId){
        Optional<User> user = userRepository.findByUserid(userId);
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);
        if(user.isEmpty()){
            ApiResponse.res(400,ErrorCode.USER_NOT_FOUND.getMessage());
        }
        if(strategy.isEmpty()){
            ApiResponse.res(400,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        if((subscribeRepository.findSubscribeByUserAndStrategy(user.get(),strategy.get())).isEmpty()){
            return ApiResponse.res(400,ErrorCode.USER_STILL_NOT_SUBSCRIBED.getMessage());
        }
        subscribeRepository.deleteByUserAndStrategy(user.get(),strategy.get());
        Strategy strategyInfo = strategy.get();
        strategyInfo.decreaseSubscriberCount();
        strategyRepository.save(strategyInfo);
        return ApiResponse.res(200,"구독 취소 완료");
    }
}
