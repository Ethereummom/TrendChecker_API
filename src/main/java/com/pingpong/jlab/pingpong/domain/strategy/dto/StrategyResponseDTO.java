package com.pingpong.jlab.pingpong.domain.strategy.dto;

import java.time.LocalDateTime;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StrategyResponseDTO {
    

    private Long strategySeq;
    private String title;
    private String content;
    private String author;
    private double startvalue;
    private String calculatedYield;
    private String assetType;
    private String thumbnailUrl;
    private Asset asset;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
    private int recommendations;
    private int subscriberCount;

    @Builder
    public StrategyResponseDTO(Long strategySeq, String title, String content, String author, double startvalue, String calculatedYield,
                               String assetType , String thumbnailUrl, LocalDateTime createdAt,LocalDateTime endedAt,
                               int recommendations ,int subscriberCount){

        this.strategySeq = strategySeq;
        this.title = title;
        this.content = content;
        this.author = author;
        this.startvalue = startvalue;
        this.calculatedYield = calculatedYield;
        this.assetType = assetType;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.recommendations = recommendations;
        this.subscriberCount = subscriberCount;

    }
}
