package com.pingpong.jlab.pingpong.domain.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/post")
public class PostController {
    
    @GetMapping
    @RequestMapping(value = "")
    public ResponseEntity getPostList(){
        return null;
    }

    @PostMapping
    @RequestMapping(value = "")
    public ResponseEntity addPost(){
        return null;
    }

    @DeleteMapping
    @RequestMapping(value ="")
    public ResponseEntity deletePost(){
        return null;
    }

    @PutMapping
    @RequestMapping(value ="")
    public ResponseEntity updatePost(){
        return null;
    }

    @GetMapping
    @RequestMapping(value ="/{post_id}")
    public ResponseEntity getPostDetail(@PathVariable Long post_id){
        return null;
    }
    
}
