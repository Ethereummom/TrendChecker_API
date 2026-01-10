package com.pingpong.jlab.pingpong.domain.llm.service;

import com.google.common.collect.ImmutableList;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GoogleSearch;
import com.google.genai.types.Tool;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GeminiService {
  @Value("${gemini.key}")
  private String apiKey;

  public ApiResponse<GenerateContentResponse> getGeminiData(){

    String prompt = "";

    Client client = Client.builder().apiKey(apiKey).build();

    Tool googleSearchTool = Tool.builder().googleSearch(GoogleSearch.builder().build()).build();

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .candidateCount(1)
            .maxOutputTokens(1024)
            .tools(ImmutableList.of(googleSearchTool))
            .build();

    GenerateContentResponse response = client.models.generateContent(
        "gemini-2.0-flash",
        "2025년 6월 12일 현 시점 코스피의 외국인 순매수와 순매도 값을 알려주세요",
        config
    );

    log.info("response data ::: " , response);

    return ApiResponse.res(200,"",response);
  }
}
