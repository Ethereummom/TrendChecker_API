package com.pingpong.jlab.pingpong.domain.subscribe.repository;

import com.pingpong.jlab.pingpong.domain.subscribe.dto.SubscribeResponseDto;
import com.pingpong.jlab.pingpong.domain.subscribe.entity.Subscribe;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;

import java.util.List;

public interface SubscribeRepositoryCustom {

    public long getCountOfSubscribes(Long strategySeq);

    public PaginationResponseDto<SubscribeResponseDto> getSubscribeListByUser(Long userSeq, PaginationRequestDto dto);

    public List<Subscribe> findByUidAndSid(Long userSeq, Long strategySeq);

}
