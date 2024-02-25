package com.pingpong.jlab.pingpong.domain.subscribe.repository;

import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.subscribe.entity.Subscribe;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>, SubscribeRepositoryCustom {


    List<Subscribe> findSubscribeByUserAndStrategy(User user, Strategy strategy);

    void deleteByUserAndStrategy(User user, Strategy strategy);

}
