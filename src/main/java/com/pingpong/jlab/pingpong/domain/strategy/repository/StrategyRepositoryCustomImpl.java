package com.pingpong.jlab.pingpong.domain.strategy.repository;

import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.pingpong.jlab.pingpong.domain.strategy.entity.QStrategy.*;

public class StrategyRepositoryCustomImpl extends QuerydslRepositorySupport implements StrategyRepositoryCustom {

    public StrategyRepositoryCustomImpl(){super(Strategy.class);}

}
