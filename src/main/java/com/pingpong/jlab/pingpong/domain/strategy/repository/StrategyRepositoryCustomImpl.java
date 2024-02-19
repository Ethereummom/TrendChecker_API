package com.pingpong.jlab.pingpong.domain.strategy.repository;

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
                    query.orderBy(strategy.recommendations.desc());
        List<Strategy> strategyList = query.fetch();
        long count = query.fetchCount();
        PaginationResponseDto<Strategy> strategyListWithPaging = new PaginationResponseDto<>(strategyList, count, dto);
        log.info("datalist : : " + strategyListWithPaging);
        return strategyListWithPaging;
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
}
