package com.pingpong.jlab.pingpong.domain.follow.converter;

import com.pingpong.jlab.pingpong.domain.follow.dto.FollowResponseDto;
import com.pingpong.jlab.pingpong.domain.follow.entity.Follow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FollowDtoConverter {

    private FollowDtoConverter(){}

    public static FollowResponseDto convert(Follow follow){
        return Optional.ofNullable(follow)
                .map(flr -> followResponseDtoBuilder(follow))
                .orElse(null);
    }

    public static List<FollowResponseDto> convert(List<Follow> followList){
        if(followList.isEmpty()){
            return new ArrayList<>();
        }
        return followList.stream()
                .map(FollowDtoConverter::followResponseDtoBuilder)
                .collect(Collectors.toList());
    }


    private static FollowResponseDto followResponseDtoBuilder(Follow follow){
        return FollowResponseDto.builder()
                .userNickname(follow.getFromUser().getNickname())
                .build();
    }
}
