package com.pingpong.jlab.pingpong.domain.post.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pingpong.jlab.pingpong.domain.post.dto.PostDto;
import com.pingpong.jlab.pingpong.domain.post.dto.PostUpdateDto;
import com.pingpong.jlab.pingpong.domain.post.entity.Post;
import com.pingpong.jlab.pingpong.domain.post.repository.PostRepository;
import com.pingpong.jlab.pingpong.domain.user.entity.User;
import com.pingpong.jlab.pingpong.domain.user.repository.UserRepository;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse getPostList(PaginationRequestDto dto){

        List<Post> postEntity2 = postRepository.getPostListWithPaging(dto.getOffset(), dto.getLimit());
        List<Post> postEntity = postRepository.findAll();
        long count = postRepository.count();
        PaginationResponseDto<PostDto> PostList = new PaginationResponseDto(postEntity2, count, dto);

        if(PostList.getDataList().isEmpty()){

            return ApiResponse.res(204,"데이터가 존재하지 않습니다.",PostList);
        }
        else{
            return ApiResponse.res(200, "게시물 리스트" , PostList);
        }
}

    public ApiResponse addPost(PostDto dto, String userid){
        
        Post postEntity = dto.dtoToEntity(dto);
        Optional<User> user = userRepository.findByUserid(userid);
        postEntity.setUser(user.get());
        postRepository.save(postEntity);

        return ApiResponse.res(200, "게시물 등록 완료");
        
    }

    public ApiResponse deletePost(Long postseq){
        
        postRepository.deleteById(postseq);
        return ApiResponse.res(200, "삭제 완료");

    }

    public ApiResponse updatePost(PostUpdateDto dto){

        Post postEntity = dto.dtoToEntity(dto);
        postRepository.save(postEntity);

        return ApiResponse.res(200, "게시물 업데이트 완료");
    }

    public ApiResponse getPostDetail(Long postseq){

        Optional<Post> post = postRepository.findById(postseq);
        if(post.isPresent()){
            return ApiResponse.res(200, "게시물 상세 조회 완료", post.get());
        }
        else{
            return ApiResponse.res(204, "게시물이 삭제되었거나 존재하지 않습니다.");
        }
    }
    
}
