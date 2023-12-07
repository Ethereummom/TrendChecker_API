package com.pingpong.jlab.pingpong.global.s3.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pingpong.jlab.pingpong.global.s3.config.S3Config;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    @Autowired
    private final S3Config s3Config;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${s3.user.path}")
    private String s3Path;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss");


    public Map<String, Serializable> uploadToS3(MultipartFile multipartFile) throws IOException{

        String fileName = multipartFile.getOriginalFilename();
        Map<String, Serializable> result = new HashMap<>();

        try (InputStream inputStream = multipartFile.getInputStream()){

            String encodedFileName = LocalDateTime.now().format(formatter) + "_" + fileName;
            String type = multipartFile.getContentType();
            String objectKey = encodedFileName;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, objectKey, inputStream, metadata);
            s3Config.amazonS3Client().putObject(putObjectRequest);

            String path = s3Path + objectKey;

            result.put("fileName" , fileName);
            result.put("encodedFileName", encodedFileName);
            result.put("path", path);
            result.put("type", type);
            result.put("uploaded", true);
            
        }catch(IOException e){
            result.put("uploaded", false);
            result.put("error", "파일 업로드 도중에 오류 발생");
        }

        return result;
    }


    
}
