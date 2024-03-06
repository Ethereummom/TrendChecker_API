package com.pingpong.jlab.pingpong.domain.strategy.repository;

import com.pingpong.jlab.pingpong.domain.strategy.converter.StrategyDtoConverter;
import com.pingpong.jlab.pingpong.domain.strategy.dto.StrategyResponseDTO;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

import static com.pingpong.jlab.pingpong.domain.strategy.entity.QStrategy.*;
@Log4j2
public class StrategyRepositoryCustomImpl extends QuerydslRepositorySupport implements StrategyRepositoryCustom {

    public StrategyRepositoryCustomImpl(){super(Strategy.class);}

    /**
     * 사용자가 투자전략 화면에서 자산별 전략 조회 버튼 클릭 시 호출하는 메서드
     * */
    @Override
    public PaginationResponseDto<Strategy> findStrategyByCategory(PaginationRequestDto dto){
        String category = dto.getCategory();
        JPQLQuery<Strategy> query = from(strategy)
                .where(strategySortByCategory(category))
                .offset(dto.getOffset())
                .limit(dto.getLimit());
        switch(category.toUpperCase()){
            case "DATE":
                query.orderBy(strategy.createdAt.desc());
            case "RECOMMEND":
                query.orderBy(strategy.recommendations.desc());
            case "SUBSCRIBERS":
                query.orderBy(strategy.subscriberCount.desc());
            case "YIELD":
                query.orderBy(strategy.calculatedYield.desc());
            default:
                query.orderBy(strategy.recommendations.desc());
        }
        List<Strategy> strategyList = query.fetch();
        long count = query.fetchCount();
        PaginationResponseDto<Strategy> strategyListWithPaging = new PaginationResponseDto<>(strategyList, count, dto);
        log.info("datalist : : " + strategyListWithPaging);
        return strategyListWithPaging;
    }

    /**
     *
     * 사용자가 투자전략 검색 시 조건에 따라 데이터 출력하는 쿼리
     * @param dto
     * @return Strategy List Sorted By SearchOptions And Keyword
     *
     */
    @Override
    public PaginationResponseDto<StrategyResponseDTO> findStrategyByCategoryAndKeyword(PaginationRequestDto dto){
        JPQLQuery<Strategy> query = from(strategy)
                .where(searchWithCategory(dto))
                .where(strategy.endYn.eq("N"))
                .offset(dto.getOffset())
                .limit(dto.getLimit());
        query.orderBy(strategy.createdAt.desc());
        List<Strategy> strategyList = query.fetch();
        long count = query.fetchCount();
        return new PaginationResponseDto<>(StrategyDtoConverter.convert(strategyList), count, dto);
    }

    /**
     * BooleanExpression 조건식
     * */
    private BooleanExpression strategySortByCategory(String category){
        log.info("category : : : " + category);
        if(Objects.isNull(category)){
            return null;
        }
        return strategy.asset.symbol.eq(category);
    }

    private BooleanExpression searchWithCategory(PaginationRequestDto dto){
        String category = dto.getCategory();

        log.info("category ::: " + category + " | keyword ::: " + dto.getKeyword() + " | page :::" + dto.getPage());
        if(category == null){
            return null;
        }
        if(dto.getKeyword() == null){
            return null;
        }

        switch(category.toUpperCase()){
            case "ALL" :
                return strategy.asset.symbol.contains(dto.getKeyword())
                        .or(strategy.title.contains(dto.getKeyword()))
                        .or(strategy.content.contains(dto.getKeyword()))
                        .or(strategy.user.nickname.contains(dto.getKeyword()));
            case "TITLE" :
                return strategy.title.contains(dto.getKeyword());
            case "CONTENT" :
                return strategy.content.contains(dto.getKeyword());
            case "TITLEANDCONTENT":
                return strategy.title.contains(dto.getKeyword())
                        .or(strategy.content.contains(dto.getKeyword()));
            case "AUTHOR" :
                return strategy.user.nickname.contains(dto.getKeyword());
            case "TYPE" :
                return strategy.asset.symbol.contains(dto.getKeyword());
            default :
                return null;
        }
    }
}
