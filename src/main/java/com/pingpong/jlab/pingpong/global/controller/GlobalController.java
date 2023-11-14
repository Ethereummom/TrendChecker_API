package com.pingpong.jlab.pingpong.global.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GlobalController {

    @RequestMapping("/login")
    public String userLogin(){
        return null;
    }

}
