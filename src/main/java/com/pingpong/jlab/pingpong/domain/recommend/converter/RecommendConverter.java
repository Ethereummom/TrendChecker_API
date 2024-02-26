package com.pingpong.jlab.pingpong.domain.recommend.converter;

import com.pingpong.jlab.pingpong.domain.recommend.dto.RecommendDTO;
import com.pingpong.jlab.pingpong.domain.recommend.entity.Recommend;
import com.pingpong.jlab.pingpong.domain.recommend.service.RecommendService;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class RecommendConverter {

    private RecommendConverter(){}

    public static RecommendDTO convert(Recommend recommend, String type){
        return Optional.ofNullable(recommend)
                .map(rec -> recommendDTOBuilder(rec,type))
                .orElse(null);
    }

    public static List<RecommendDTO> convert(List<Recommend> recommendList, String type){
        if(recommendList.isEmpty()){
            return new ArrayList<>();
        }
        return recommendList.stream()
                .map(rec -> recommendDTOBuilder(rec,type))
                .collect(Collectors.toList());
    }

    private static RecommendDTO recommendDTOBuilder(Recommend recommend, String type){
        if(type == "POST"){
            log.info("recommendEntity :::: {} , type = " +type , recommend);
            return RecommendDTO.builder()
                    .recSeq(recommend.getRecSeq())
                    .title(recommend.getPost().getTitle())
                    .entitySeq(recommend.getPost().getPostseq())
                    .content(recommend.getPost().getContent())
                    .category(recommend.getPost().getCategory())
                    .createdAt(recommend.getCreatedAt())
                    .updatedAt(recommend.getUpdatedAt())
                    .build();
        }
        log.info("recommendEntity: : : : :{}",recommend);
        return RecommendDTO.builder()
                .recSeq(recommend.getRecSeq())
                .title(recommend.getStrategy().getTitle())
                .entitySeq(recommend.getStrategy().getStrategySeq())
                .content(recommend.getStrategy().getContent())
                .category(recommend.getStrategy().getAsset().getCategory())
                .createdAt(recommend.getCreatedAt())
                .updatedAt(recommend.getUpdatedAt())
                .build();
    }
}
