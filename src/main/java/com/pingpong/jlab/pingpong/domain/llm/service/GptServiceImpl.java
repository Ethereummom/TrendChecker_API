package com.pingpong.jlab.pingpong.domain.llm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingpong.jlab.pingpong.domain.llm.dto.GptChatMessage;
import com.pingpong.jlab.pingpong.domain.llm.dto.GptOpenApiRequestDto;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Tag(name="GPT Service" , description ="GPT Service IF")
@Log4j2
@Service
public class GptServiceImpl implements GptService {

  /**
   * Get Gpt Data
   *
   * */
  @Override
  public ApiResponse getGptData(String prompt, String type) {

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new org.springframework.http.HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(OPENAI_API_KEY);

    String newPrompt = "Optional 은 객체에 Null 이 저장되어 NullPointException 이 발생하는 것을 방지하기 위한 객체. Optional 객체에 저장된 데이터를 꺼내올 때 만약 Null 이라면 NullPointException 이 발생하는 것이 아니라 개발자가 지정한 커스텀 예외를 발생시키거나 디폴트 값을 가져오도록 후처리를 할 수 있다는 장점이 있다. 를 영문으로 번역해서 부가 설명까지 곁들여줘.";

//    Map<String, Object> message = Map.of("role", "user", "content", "prompt");
//    Map<String, Object> requestBody = Map.of(
//        "model", "gpt-3.5-turbo","messages", List.of(message)
//    );
    GptChatMessage message = new GptChatMessage("user", newPrompt);
    GptOpenApiRequestDto requestBody = new GptOpenApiRequestDto("gpt-4o", List.of(message), false);

    HttpEntity<GptOpenApiRequestDto> request = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);

    String content = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(response.getBody());
      content = root.path("choices").get(0).path("message").path("content").asText();
    } catch (Exception e) {
      log.error("Failed to parse GPT response", e);
      content = "응답 파싱 실패";
    }


    log.info("response from openAI :: " , content);
    return ApiResponse.res(200, "ChatGpt 호출 완료" ,content);
  }
}
