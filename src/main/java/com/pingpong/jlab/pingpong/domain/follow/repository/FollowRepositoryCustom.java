package com.pingpong.jlab.pingpong.domain.follow.repository;

import com.pingpong.jlab.pingpong.domain.follow.dto.FollowResponseDto;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;

public interface FollowRepositoryCustom {

    PaginationResponseDto<FollowResponseDto> getFollowerListByUser(User user, PaginationRequestDto dto);

}
