package com.pingpong.jlab.pingpong.domain.asset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.lang.Long;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetseq;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private Double currentprice;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(length = 20, nullable = false)
    private String risk;

    @Column(length = 255, nullable = true)
    private String thumbnailUrl;

    public Asset(int assetseq, String name, String symbol, Double currentprice, String category, String risk){
        
        this.assetseq = Long.valueOf(assetseq);
        this.name = name;
        this.symbol = symbol;
        this.currentprice = currentprice;
        this.category = category;
        this.risk = risk;

    }
    
}
