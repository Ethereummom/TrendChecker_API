package com.pingpong.jlab.pingpong.global.file.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadDto {

    private String origin_file_name;
    private String path;
    private String type;
    private Long ref_id;
    private String ref_entity;
    
}
