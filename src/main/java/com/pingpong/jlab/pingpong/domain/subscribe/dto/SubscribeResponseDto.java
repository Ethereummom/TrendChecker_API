package com.pingpong.jlab.pingpong.domain.subscribe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SubscribeResponseDto {

    private String title;
    private String author;
    private String ticker;
    private double yield;
    private Long subscriberCount;

    @Builder
    public SubscribeResponseDto(String title, String author, String ticker, double yield, Long subscriberCount){
        this.title = title;
        this.author = author;
        this.ticker = ticker;
        this.yield = yield;
        this.subscriberCount = subscriberCount;
    }


}
