package com.pingpong.jlab.pingpong.domain.asset.service;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.asset.repository.AssetRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;

    @Transactional
    public ApiResponse updateAssetInfo(String symbol){
        if(symbol == null){
            return ApiResponse.res(500, ErrorCode.MISSING_INPUT_VALUE.getMessage());
        }
        Optional<Asset> assetInfo = assetRepository.findBySymbol(symbol);
        Asset asset = assetInfo.get();

        /**
         * FASTAPI Data Treating Server에 데이터 갱신 요청
         * */


        return ApiResponse.res(200, "자산 데이터 업데이트 완료");
    }
}
