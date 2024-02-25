package com.pingpong.jlab.pingpong.domain.subscribe.service;

import com.google.protobuf.Api;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.strategy.repository.StrategyRepository;
import com.pingpong.jlab.pingpong.domain.subscribe.dto.SubscribeResponseDto;
import com.pingpong.jlab.pingpong.domain.subscribe.entity.Subscribe;
import com.pingpong.jlab.pingpong.domain.subscribe.repository.SubscribeRepository;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubscribeService {

    @Autowired
    SubscribeRepository subscribeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StrategyRepository strategyRepository;

    @Transactional
    public ApiResponse doSubscribe(Long strategySeq, String userInfo){
        Optional<User> user = userRepository.findByUserid(userInfo);
        if(user.isEmpty()){
            return ApiResponse.res(400,ErrorCode.USER_NOT_FOUND.getMessage());
        }
        /** 이미 구독중인지 아닌지 여부 검증 */
        Long userSeq = user.get().getUserseq();
        List<Subscribe>filter = subscribeRepository.findByUidAndSid(userSeq, strategySeq);
        if(filter != null && !filter.isEmpty()){
            return ApiResponse.res(409, ErrorCode.USER_ALREADY_SUBSCRIBED.getMessage());
        }
        Optional<Strategy> strategy = strategyRepository.findById(strategySeq);

        if(strategy.isEmpty()){
            return ApiResponse.res(400,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        /** 추천수 증가 처리 */
        Strategy strategyEntity = strategy.get();
        strategyEntity.addSubscriberCount();
        strategyRepository.save(strategyEntity);

        /** 구독 정보 테이블에 저장 */
        Subscribe subscribe = Subscribe.builder()
                .user(user.get())
                .strategy(strategyEntity)
                .build();

        subscribeRepository.save(subscribe);
        return ApiResponse.res(204,"구독 완료");
    }

    public ApiResponse getSubscribeList(PaginationRequestDto dto, String userInfo){
        Optional<User> user = userRepository.findByUserid(userInfo);
        if(user.isEmpty()){
            return ApiResponse.res(403, ErrorCode.USER_NOT_FOUND.getMessage());
        }
        Long userSeq = user.get().getUserseq();
        PaginationResponseDto<SubscribeResponseDto> usersActiveSubscribeList = subscribeRepository.getSubscribeListByUser(userSeq , dto);
        return ApiResponse.res(200, "해당 유저의 구독 전략 리스트", usersActiveSubscribeList);
    }

    public long getSubscribeList(){
        return subscribeRepository.count();
    }
}
