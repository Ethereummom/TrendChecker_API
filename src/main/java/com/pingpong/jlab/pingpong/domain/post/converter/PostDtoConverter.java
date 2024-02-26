package com.pingpong.jlab.pingpong.domain.post.converter;
import com.pingpong.jlab.pingpong.domain.post.dto.PostResponseDto;
import com.pingpong.jlab.pingpong.domain.post.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostDtoConverter {

    private PostDtoConverter(){}

    public static PostResponseDto convert(Post post){
        return Optional.ofNullable(post)
                .map(PostDtoConverter::postResponseDtoBuilder)
                .orElse(null);
    }

    public static List<PostResponseDto> convert(List<Post> postList){
        if(postList.isEmpty()){
            return new ArrayList<>();
        }
        return postList.stream()
                .map(PostDtoConverter::postResponseDtoBuilder)
                .collect(Collectors.toList());
    }

    private static PostResponseDto postResponseDtoBuilder(Post post){
        return PostResponseDto.builder()
                .postseq(post.getPostseq())
                .category(post.getCategory())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .content(post.getContent())
                .created_At(post.getCreatedAt())
                .updated_At(post.getUpdatedAt())
                .build();
    }

}
