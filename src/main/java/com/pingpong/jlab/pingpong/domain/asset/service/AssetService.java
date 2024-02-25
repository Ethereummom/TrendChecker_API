package com.pingpong.jlab.pingpong.domain.asset.service;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.asset.repository.AssetRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

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
        if(assetInfo.isEmpty()){
            return ApiResponse.res(500,ErrorCode.ENTITY_NOT_FOUND.getMessage());
        }
        Asset asset = assetInfo.get();
        asset.setCurrentprice(asset.getCurrentprice() * new Random().nextDouble(0.7,1.3));
        assetRepository.save(asset);

        /**
         * FASTAPI Data Treating Server에 데이터 갱신 요청
         * */

        return ApiResponse.res(200, "자산 데이터 업데이트 완료");
    }

    public ApiResponse updateWholeAssetInfo(){
        /** FastAPI endpoint 호출해서
                전체 자산 데이터 업데이트 실시*/
        return null;
    }
}
