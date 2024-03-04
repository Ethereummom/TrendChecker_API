package com.pingpong.jlab.pingpong.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FollowRequestDto {

    private String targetUserId;
    private String followOrUnfollow;

}
