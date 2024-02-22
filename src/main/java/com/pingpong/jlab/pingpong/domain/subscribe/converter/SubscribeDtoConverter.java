package com.pingpong.jlab.pingpong.domain.subscribe.converter;

import com.pingpong.jlab.pingpong.domain.subscribe.dto.SubscribeResponseDto;
import com.pingpong.jlab.pingpong.domain.subscribe.entity.Subscribe;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class SubscribeDtoConverter {

    private SubscribeDtoConverter(){}

    public static SubscribeResponseDto convert(Subscribe subscribe, Long subscribersCount){
        return Optional.ofNullable(subscribe)
                .map(sub -> subscribeDtoBuilder(sub, subscribersCount))
                .orElse(null);
    }

    public static List<SubscribeResponseDto> convert(List<Subscribe> subscribeList , Long subscribersCount){
        if(subscribeList.isEmpty()){
            return new ArrayList<>();
        }
        return subscribeList.stream()
                .map(sub -> subscribeDtoBuilder(sub, subscribersCount))
                .collect(Collectors.toList());
    }

    private static SubscribeResponseDto subscribeDtoBuilder(Subscribe subscribe , Long subscribersCount){
        log.info("subscribeEntity : : : : : {} " , subscribe);

        return SubscribeResponseDto.builder()
                .title(subscribe.getStrategy().getTitle())
                .author(subscribe.getStrategy().getUser().getNickname())
                .ticker(subscribe.getStrategy().getAsset().getSymbol())
                .yield(subscribe.getStrategy().getCalculatedYield())
                .subscriberCount(subscribersCount)
                .build();
    }
}
