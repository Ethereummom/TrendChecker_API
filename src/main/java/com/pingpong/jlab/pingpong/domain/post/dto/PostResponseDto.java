package com.pingpong.jlab.pingpong.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import java.lang.Long;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {

    private Long postseq;
    private String category;
    private String content;
    private String title;
    private LocalDateTime created_At;
    private LocalDateTime updated_At;
    
}
