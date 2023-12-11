package com.pingpong.jlab.pingpong.domain.strategy.dto;

import java.time.LocalDateTime;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StrategyResponseDTO {
    

    private Long strategySeq;
    private String title;
    private String content;
    private double startvalue;
    private double calculatedYield;
    private String assetType;
    private Asset asset;
    private User user;
    private LocalDateTime createdAt;
    
    
}
