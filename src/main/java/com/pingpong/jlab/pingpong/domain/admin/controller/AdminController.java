package com.pingpong.jlab.pingpong.domain.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/admin")
public class AdminController {

    @GetMapping(value="")
    public ResponseEntity getAdminList(){
        return null;
    }

    @PostMapping(value="")
    public ResponseEntity addAdmin(){
        return null;
    }

    @DeleteMapping(value="")
    public ResponseEntity deleteAdmin(){
        return null;
    }

    @PutMapping(value="")
    public ResponseEntity updateAdmin(){
        return null;
    }

    @GetMapping(value="/{admin_id}")
    public ResponseEntity adminDetail(@PathVariable Long admin_id){
        return null;
    }
    
}
