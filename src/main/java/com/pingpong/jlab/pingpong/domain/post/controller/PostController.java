package com.pingpong.jlab.pingpong.domain.post.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pingpong.jlab.pingpong.domain.post.dto.PostDto;
import com.pingpong.jlab.pingpong.domain.post.dto.PostUpdateDto;
import com.pingpong.jlab.pingpong.domain.post.service.PostService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    PostService postService;
    
    @GetMapping(value = "")
    public ApiResponse getPostList(PaginationRequestDto dto,@AuthenticationPrincipal JwtAuthentication userinfo){
        
        return postService.getPostList(dto);
    }

    @PostMapping(value = "")
    public ApiResponse addPost(@RequestBody PostDto dto, @AuthenticationPrincipal JwtAuthentication userinfo){
        
        return postService.addPost(dto, userinfo.getUserid());
    }

    @DeleteMapping(value ="/{postseq}")
    public ApiResponse deletePost(@PathVariable("postseq") Long postseq){
        return postService.deletePost(postseq);
    }

    @PutMapping(value ="")
    public ApiResponse updatePost(@RequestBody PostUpdateDto dto){
        return postService.updatePost(dto);
    }

    @GetMapping(value ="/{postseq}")
    public ApiResponse getPostDetail(@PathVariable("postseq") Long postseq){
        return postService.getPostDetail(postseq);
    }
    
}
