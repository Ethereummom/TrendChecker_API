package com.pingpong.jlab.pingpong.domain.strategy.dto;

import java.time.LocalDateTime;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrategyDTO {

    private Long strategySeq;

    private String title;
    private String content;
    private double startvalue;
    private double calculatedYield;
    private String assetType;
    private Asset asset;
    private User user;
    private LocalDateTime createdAt;

    public Strategy dtoToEntity(StrategyDTO dto){
        Strategy strategy = new Strategy();
        strategy.setTitle(dto.getTitle());
        strategy.setContent(dto.getContent());
        strategy.setStartvalue(dto.getStartvalue());
        strategy.setCalculatedYield(0.0);
        strategy.setUser(dto.getUser());
        strategy.setEndYn("N");
        strategy.setRecommendations(0);
        strategy.setAsset(dto.getAsset());

        return strategy;
    }
}
