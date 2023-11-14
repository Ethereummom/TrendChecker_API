package com.pingpong.jlab.pingpong.domain.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pingpong.jlab.pingpong.domain.board.entity.Board;

@RestController
@RequestMapping("/board")
public class BoardController {

    public List<Board> getBoardList(){
        return null;

    }
    
}
