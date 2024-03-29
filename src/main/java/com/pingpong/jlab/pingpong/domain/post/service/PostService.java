package com.pingpong.jlab.pingpong.domain.post.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.pingpong.jlab.pingpong.domain.post.dto.PostResponseDto;
import com.pingpong.jlab.pingpong.domain.post.repository.PostRepositoryCustomImpl;
import com.pingpong.jlab.pingpong.domain.strategy.entity.Strategy;
import com.pingpong.jlab.pingpong.domain.strategy.repository.StrategyRepository;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
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

    @Autowired
    StrategyRepository strategyRepository;

    public ApiResponse getPostList(PaginationRequestDto dto){

        String category = dto.getCategory();
        String keyword = dto.getKeyword();
        if(category.equals("stgy")){
            Long stgySeq = Long.parseLong(dto.getKeyword());
            Optional<Strategy> stgy = strategyRepository.findById(stgySeq);
            if(stgy.isEmpty()){
                return ApiResponse.res(400,ErrorCode.USER_NOT_FOUND.getMessage());
            }
            return ApiResponse.res(200,"해당 전략에 따른 게시물리스트" , postRepository.getPostListByStgySeq(dto,stgy.get()));
        }


        PaginationResponseDto<PostResponseDto> postList = postRepository.getPostListWithSearchAndPaging(dto);
    
        if(postList.getDataList().isEmpty()){

            return ApiResponse.res(204,"데이터가 존재하지 않습니다.",postList);
        }
        else{
            return ApiResponse.res(200, "게시물 리스트" , postList);
        }
}
    public ApiResponse getPostListByCategoryAndRank(String category){
        if(category == null){
            return ApiResponse.res(400, ErrorCode.INVALID_INPUT_VALUE.getMessage());
        }
        return ApiResponse.res(200,"인기 게시물 리스트",postRepository.getPostListByCategoryAndRank(category));
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
