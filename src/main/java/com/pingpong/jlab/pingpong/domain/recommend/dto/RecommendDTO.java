package com.pingpong.jlab.pingpong.domain.recommend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RecommendDTO {

    private Long recSeq;
    private Long entitySeq;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public RecommendDTO(Long recSeq, Long entitySeq, String title, String content, String category ,LocalDateTime createdAt, LocalDateTime updatedAt){
        this.recSeq = recSeq;
        this.entitySeq = entitySeq;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
