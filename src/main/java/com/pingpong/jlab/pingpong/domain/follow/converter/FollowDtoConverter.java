package com.pingpong.jlab.pingpong.domain.follow.converter;

import com.pingpong.jlab.pingpong.domain.follow.dto.FollowResponseDto;
import com.pingpong.jlab.pingpong.domain.follow.entity.Follow;

import java.util.Optional;

public class FollowDtoConverter {

    private FollowDtoConverter(){}

//    public static FollowResponseDto convert(Follow follow){
//        return Optional.ofNullable(follow)
//                .map()
//                .orElse(null);
//    }


    private static FollowResponseDto followResponseDtoBuilder(Follow follow){
        return FollowResponseDto.builder()
                .userNickname(follow.getToUser().getNickname())
                .build();
    }
}
