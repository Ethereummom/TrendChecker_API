package com.pingpong.jlab.pingpong.domain.record.dto;


import java.time.LocalDateTime;

import com.pingpong.jlab.pingpong.domain.asset.entity.Asset;
import com.pingpong.jlab.pingpong.domain.record.entity.Record;
import com.pingpong.jlab.pingpong.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecordRequestDto {


    private Long recordseq;
    private int startprice;
    private int currentprice;
    private LocalDateTime finished_at;
    private double percentage;
    private User user;
    private Asset asset;


    public Record DtoToEntity(RecordRequestDto dto){

        Record record = new Record();
        record.setStartprice(startprice);
        record.setCurrentprice(currentprice);
        record.setFinishedAt(finished_at);
        record.setPercentage(percentage);
        record.setUser(user);
        record.setAsset(asset);

        return record;

    }
    
}
