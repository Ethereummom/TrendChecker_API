package com.pingpong.jlab.pingpong.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.global.security.oauth2.Provider;

import java.util.List;


@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserseq(Long userseq);

    Optional<User> findByUserid(String userid);

    @Override
    default List<User> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
    }

    List<User> findByRole(String role);

    Optional<User> findByProviderAndUserid(Provider provider , String userid);


    
}
