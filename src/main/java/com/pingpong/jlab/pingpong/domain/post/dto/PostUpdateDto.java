package com.pingpong.jlab.pingpong.domain.post.dto;

import com.pingpong.jlab.pingpong.domain.post.entity.Post;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostUpdateDto {


    private Long postseq;
    private String title;
    private String content;
    private String category;



    public Post dtoToEntity(PostUpdateDto dto){

        Post post = new Post();

        post.setPostseq(postseq);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());

        return post;

    }
}
