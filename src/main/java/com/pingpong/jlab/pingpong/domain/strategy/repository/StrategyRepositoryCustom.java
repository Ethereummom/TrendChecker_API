package com.pingpong.jlab.pingpong.domain.strategy.repository;

import com.pingpong.jlab.pingpong.domain.strategy.dto.StrategyResponseDTO;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;

import java.util.List;

public interface StrategyRepositoryCustom {

    public PaginationResponseDto<Strategy> findStrategyByCategory(PaginationRequestDto dto);

    public PaginationResponseDto<StrategyResponseDTO> findStrategyByCategoryAndKeyword(PaginationRequestDto dto);
}
