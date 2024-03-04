package com.pingpong.jlab.pingpong.domain.follow.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FollowResponseDto {

    private String userNickname;
    private int userFollowerCount;
    private String profileImageUrl;
    private double cumulativeYield;

    @Builder
    public FollowResponseDto(String userNickname, int userFollowerCount, String profileImageUrl, double cumulativeYield){
        this.userNickname = userNickname;
        this.userFollowerCount = userFollowerCount;
        this.profileImageUrl = profileImageUrl;
        this.cumulativeYield = cumulativeYield;
    }
}
