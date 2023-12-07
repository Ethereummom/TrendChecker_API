package com.pingpong.jlab.pingpong.domain.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.Long;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetseq;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private double currentprice;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(length = 20, nullable = false)
    private String risk;

    @Column(length = 255, nullable = true)
    private String thumbnailUrl;

    public Asset(int assetseq, String name, String symbol, double currentprice, String category, String risk){
        
        this.assetseq = Long.valueOf(assetseq);
        this.name = name;
        this.symbol = symbol;
        this.currentprice = currentprice;
        this.category = category;
        this.risk = risk;

    }
    
}
