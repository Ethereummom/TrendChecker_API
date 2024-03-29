package com.pingpong.jlab.pingpong.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.lang.Long;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostResponseDto {

    private Long postseq;
    private String category;
    private String title;
    private String content;
    private String author;
    private int recommend;
    private LocalDateTime created_At;
    private LocalDateTime updated_At;

    @Builder
    public PostResponseDto(Long postseq, String category, String title, String content, String author, int recommend, LocalDateTime created_At, LocalDateTime updated_At){

        this.postseq = postseq;
        this.category = category;
        this.title = title;
        this.content = content;
        this.author = author;
        this.recommend = recommend;
        this.created_At = created_At;
        this.updated_At = updated_At;

    }
}
