package com.pingpong.jlab.pingpong.domain.record.service;

import java.util.List;
import java.util.Optional;
import java.lang.Long;

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
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse getUserRecordDetail(String userinfo,Long recordseq){
        User user = userRepository.findByUserid(userinfo).get();
        Record record = recordRepository.findById(recordseq).get();
        record.setPercentage((record.getCurrentprice() - record.getStartprice()) / record.getStartprice() * 100);

        return ApiResponse.res(200, "레코드 상세", record);
    }
    

    public ApiResponse getUserRecord(PaginationRequestDto dto, String userinfo){

        User user = userRepository.findByUserid(userinfo).get();
        List<Record> records = recordRepository.findByUser(user);
        if(!records.isEmpty()){
            for(Record record : records){
                record.setPercentage((record.getCurrentprice() - record.getStartprice()) / record.getStartprice() * 100);
            }
        long count = recordRepository.count();
        PaginationResponseDto<Record> recordList = new PaginationResponseDto<>(records, count,dto);

        
            return ApiResponse.res(200, "Trading Record", recordList);
        }
        else{
            return ApiResponse.res(204,"데이터가 존재하지 않습니다");
        }

    }

    public ApiResponse addUserRecord(RecordRequestDto recordInfo, String userinfo){
        
        Optional<User> user= userRepository.findByUserid(userinfo);
        Asset asset = new Asset(1,"bond","US10Y",42110.5,"US10Y","25");
        User recUser = user.get();
        recordInfo.setUser(recUser);
        // recordInfo.setAsset(asset);
        Record record = recordInfo.DtoToEntity(recordInfo);
        recordInfo.setPercentage(0);

        Record result = recordRepository.save(record);
        if (result.getUser().getUserid().equals(userinfo)){
            return ApiResponse.res(200, "거래 데이터 등록 완료");
        }
        else{
            return ApiResponse.res(500, "예기치 못한 에러 발생");
        }


    }
}
