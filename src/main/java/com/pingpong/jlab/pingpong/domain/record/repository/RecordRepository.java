package com.pingpong.jlab.pingpong.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pingpong.jlab.pingpong.domain.record.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record , Long>{

    @Override
    default List<Record> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
