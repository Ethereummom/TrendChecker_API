package com.pingpong.jlab.pingpong.domain.subscribe.repository;

import com.pingpong.jlab.pingpong.domain.subscribe.converter.SubscribeDtoConverter;
import com.pingpong.jlab.pingpong.domain.subscribe.dto.SubscribeResponseDto;
import com.pingpong.jlab.pingpong.domain.subscribe.entity.Subscribe;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.pingpong.jlab.pingpong.domain.subscribe.entity.QSubscribe.*;
@Log4j2
public class SubscribeRepositoryCustomImpl extends QuerydslRepositorySupport implements SubscribeRepositoryCustom {

    public SubscribeRepositoryCustomImpl(){super(Subscribe.class);}

    @Override
    public long getCountOfSubscribes(Long strategySeq){
        JPQLQuery<Subscribe> query = from(subscribe)
                .where(subscribe.strategy.strategySeq.eq(strategySeq));
        long count = query.fetchCount();
        return count;
    }

    @Override
    public PaginationResponseDto<SubscribeResponseDto> getSubscribeListByUser(Long userSeq, PaginationRequestDto dto){
        JPQLQuery<Subscribe> query = from(subscribe)
                .where(subscribe.user.userseq.eq(userSeq))
                .where(subscribe.strategy.endYn.eq(dto.getCategory()))
                .offset(dto.getOffset())
                .limit(dto.getLimit());
        long count = query.fetchCount();
        List<Subscribe> subsList= query.fetch();

        return new PaginationResponseDto<>(SubscribeDtoConverter.convert(subsList,1L), count, dto);

    }

    @Override
    public List<Subscribe> findByUidAndSid(Long userSeq, Long strategySeq){
        JPQLQuery<Subscribe> query = from(subscribe)
                .where(subscribe.user.userseq.eq(userSeq))
                .where(subscribe.strategy.strategySeq.eq(strategySeq));
        return query.fetch();
    }
}
