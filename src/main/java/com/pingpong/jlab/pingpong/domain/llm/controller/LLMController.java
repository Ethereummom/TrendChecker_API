package com.pingpong.jlab.pingpong.domain.llm.controller;

import com.pingpong.jlab.pingpong.domain.llm.dto.GptRequestDto;
import com.pingpong.jlab.pingpong.domain.llm.service.GeminiService;
import com.pingpong.jlab.pingpong.domain.llm.service.GptServiceImpl;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="GPT Controller" , description ="GPT API")
@Log4j2
@RestController
@RequestMapping("/api/v1/gpt")
public class LLMController {

  private final GptServiceImpl gptService;
  private final GeminiService geminiService;

  public LLMController(GptServiceImpl gptService, GeminiService geminiService) {
    this.gptService = gptService;
    this.geminiService = geminiService;
  }

  @PostMapping("/request")
  public ApiResponse askLLM(@RequestBody GptRequestDto reqData) {
    if ("1".equals(reqData.getReqType())) {
      return gptService.getGptData("hi", "1");
    }
    else if("2".equals(reqData.getReqType())) {
      return geminiService.getGeminiData();
    }
    else{
      return ApiResponse.res(200, "데이터가 없습니다.");
    }
  }
}
