package com.pingpong.jlab.pingpong.domain.follow.repository;

import com.pingpong.jlab.pingpong.domain.follow.converter.FollowDtoConverter;
import com.pingpong.jlab.pingpong.domain.follow.dto.FollowResponseDto;
import com.pingpong.jlab.pingpong.domain.follow.entity.Follow;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.pingpong.jlab.pingpong.domain.follow.entity.QFollow.*;
public class FollowRepositoryCustomImpl extends QuerydslRepositorySupport implements FollowRepositoryCustom {

    public FollowRepositoryCustomImpl(){super(Follow.class);}
    @Override
    public PaginationResponseDto<FollowResponseDto> getFollowerListByUser(User user , PaginationRequestDto dto){
        JPQLQuery<Follow> query = from(follow)
                .where(follow.toUser.eq(user));
        query.orderBy(follow.followSeq.desc());
        List<Follow> followList = query.fetch();
        long count = query.fetchCount();

        return new PaginationResponseDto<>(FollowDtoConverter.convert(followList),count,dto);
    }
}
