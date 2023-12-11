package com.pingpong.jlab.pingpong.domain.asset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>{

    Optional<Asset> findBySymbol(String symbol);
    
}
