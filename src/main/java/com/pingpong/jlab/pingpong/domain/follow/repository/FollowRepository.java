package com.pingpong.jlab.pingpong.domain.follow.repository;

import com.pingpong.jlab.pingpong.domain.follow.entity.Follow;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow , Long>,FollowRepositoryCustom {

    Optional<Follow> findFollowByToUserAndFromUser(User toUser, User fromUser);

    void deleteByToUserAndFromUser(User toUser, User fromUser);
}
