package com.pingpong.jlab.pingpong.domain.record.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.record.dto.RecordRequestDto;
import com.pingpong.jlab.pingpong.domain.record.entity.Record;
import com.pingpong.jlab.pingpong.domain.record.repository.RecordRepository;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    UserRepository userRepository;
    

    public ApiResponse getUserRecord(String userinfo){

        User user = userRepository.findByUserid(userinfo).get();
        List<Record> records;

        records = recordRepository.findByUser(user);

        if(!records.isEmpty()){
            return ApiResponse.res(200, "Trading Record", records);
        }
        else{
            return ApiResponse.res(204,"데이터가 존재하지 않습니다",records);
        }

    }

    public ApiResponse addUserRecord(RecordRequestDto recordInfo, String userinfo){
        
        Optional<User> user= userRepository.findByUserid(userinfo);
        Asset asset = new Asset((Long)1,"cryptocurrency","bitcoin","65","BTCUSDT");
        User recUser = user.get();
        recordInfo.setUser(recUser);
        recordInfo.setAsset(asset);
        Record record = recordInfo.DtoToEntity(recordInfo);

        Record result = recordRepository.save(record);
        if (result.getUser().equals(userinfo)){
            return ApiResponse.res(200, "거래 데이터 등록 완료");
        }
        else{
            return ApiResponse.res(500, "예기치 못한 에러 발생");
        }


    }
}
