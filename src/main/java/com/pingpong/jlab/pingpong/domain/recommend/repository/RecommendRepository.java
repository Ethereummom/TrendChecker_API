package com.pingpong.jlab.pingpong.domain.recommend.repository;

import com.pingpong.jlab.pingpong.domain.post.entity.Post;
import com.pingpong.jlab.pingpong.domain.recommend.entity.Recommend;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    List<Recommend> findRecommendByUser(User user);

    Optional<Recommend> findRecommendByUserAndStrategy(User user, Strategy strategy);

    Optional<Recommend> findRecommendByUserAndPost(User user, Post post);

    void deleteByUserAndStrategy(User user, Strategy strategy);
    void deleteByUserAndPost(User user, Post post);
}
