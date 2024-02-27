package com.pingpong.jlab.pingpong.domain.post.repository;
import java.util.List;

import com.pingpong.jlab.pingpong.domain.post.dto.PostResponseDto;
import com.pingpong.jlab.pingpong.domain.post.entity.Post;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;


public interface PostRepositoryCustom {

    PaginationResponseDto<PostResponseDto> getPostListWithSearchAndPaging(PaginationRequestDto dto);
}
