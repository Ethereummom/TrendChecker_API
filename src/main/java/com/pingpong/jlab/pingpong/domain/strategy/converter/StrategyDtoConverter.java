package com.pingpong.jlab.pingpong.domain.strategy.converter;

import com.pingpong.jlab.pingpong.domain.strategy.dto.StrategyResponseDTO;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class StrategyDtoConverter {

    private StrategyDtoConverter(){

    }

    public static StrategyResponseDTO convert(Strategy strategy){
        return Optional.ofNullable(strategy)
                .map(StrategyDtoConverter::strategyDtoBuilder)
                .orElse(null);
    }

    public static List<StrategyResponseDTO> convert(List<Strategy> strategyList){
        if(strategyList.isEmpty()){
            return new ArrayList<>();
        }
        return strategyList.stream()
                .map(StrategyDtoConverter::convert)
                .collect(Collectors.toList());
    }

    private static StrategyResponseDTO strategyDtoBuilder(Strategy strategy){

        log.info("strategyEntity :::: {}" , strategy.getUser().getNickname());

        return StrategyResponseDTO.builder()
                .strategySeq(strategy.getStrategySeq())
                .title(strategy.getTitle())
                .content(strategy.getContent())
                .author(strategy.getUser().getNickname())
                .startvalue(strategy.getStartvalue())
                .calculatedYield(strategy.getCalculatedYield())
                .assetType(strategy.getAsset().getName())
                .createdAt(strategy.getCreatedAt())
                .endedAt(strategy.getUpdatedAt())
                .recommendations(strategy.getRecommendations())
                .subscriberCount(strategy.getSubscriberCount())
                .build();
    }
}
