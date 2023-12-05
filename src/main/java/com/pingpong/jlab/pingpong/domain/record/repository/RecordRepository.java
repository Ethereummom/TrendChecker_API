package com.pingpong.jlab.pingpong.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.record.entity.Record;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

@Repository
public interface RecordRepository extends JpaRepository<Record , Long>{


    List<Record> findByUser(User user);

    List<Record> findByAsset(Asset asset);

    @Override
    default List<Record> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}
