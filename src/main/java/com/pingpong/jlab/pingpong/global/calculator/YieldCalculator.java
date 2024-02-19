package com.pingpong.jlab.pingpong.global.calculator;

public class YieldCalculator {

    // 수익률 백분율로 계산
    public static Double calculatePercentageYield(Double startPrice, Double currentPrice){
        if(startPrice == null || currentPrice == null || startPrice <= 0 || currentPrice <= 0){
            throw new IllegalArgumentException("Invalid Input Values");
        }
        return ((currentPrice - startPrice) * 100);
    }

    // 수익률 연단위로 계산
    public static Double calculatePercentageYieldByYear(Double startPrice, Double currentPrice){
        if(startPrice == null || currentPrice == null || startPrice <= 0 || currentPrice <= 0){
            throw new IllegalArgumentException("Invalid Input Values");
        }
        return ((currentPrice - startPrice) * 100);
    }
}
