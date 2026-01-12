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

import com.google.genai.Client;
import com.google.genai.types.ListModelsConfig;
import com.google.genai.types.Model;

@Log4j2
@Service
public class GeminiService {

  @Value("${gemini.key}")
  private String apiKey;

  public ApiResponse<GenerateContentResponse> getGeminiData() {

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
        "gemini-2.5-flash",
        "2025년 6월 12일 현 시점 코스피의 외국인 순매수와 순매도 값을 알려주세요",
        config
    );

    log.info("response data ::: ", response);

    return ApiResponse.res(200, "", response);
  }

  // 인자로 노션 요약 텍스트를 받도록 수정
  public String getDailyBriefing(String notionSummary) {

    printAvailableModels();

    // 3년 차 개발자의 센스: 페르소나와 제약사항을 담은 프롬프트 엔지니어링
    String prompt = String.format("""
        너는 사용자의 일정을 관리하는 유능하고 다정한 AI 비서야.
        아래 제공된 '노션 일정 데이터'를 바탕으로 활기찬 아침 브리핑 문구를 작성해줘.
                    
        [노션 일정 데이터]
        %s
                    
        [작성 가이드라인]
        1. 오늘의 날짜와 함께 다정한 인사를 건네줘.
        2. 할 일들을 중요도나 시간 순서에 따라 자연스럽게 요약해줘.
        3. 마지막에는 사용자를 응원하는 한 문장을 포함해줘.
        4. 너무 딱딱하지 않게 이모지를 적절히 섞어서 친근한 말투로 써줘.
        """, notionSummary);

    Client client = Client.builder().apiKey(apiKey).build();

    // 2.0 Flash 모델 호출
    GenerateContentResponse response = client.models.generateContent(
        "gemini-2.5-flash",
        prompt, // 동적 프롬프트 주입
        GenerateContentConfig.builder()
            .candidateCount(1)
            .maxOutputTokens(4096)
            .build()
    );

    // 1. 후보군이 있는지 확인
    if (response.candidates().isPresent() && !response.candidates().get().isEmpty()) {
      var candidate = response.candidates().get().get(0);

      // 2. 컨텐츠가 있는지 확인
      if (candidate.content().isPresent()) {
        var content = candidate.content().get();

        // 3. 파트가 있고 텍스트가 있는지 확인
        if (content.parts().isPresent() && !content.parts().get().isEmpty()) {
          // [수정 핵심] text() 뒤에 .orElse("")를 붙여서 String으로 변환!
          return content.parts().get().get(0).text().orElse("");
        }
      }
    }

    return "브리핑 내용을 생성할 수 없습니다.";
  }

// ...

  public void printAvailableModels() {
    log.info(">>> Google GenAI 모델 리스트 조회를 시작합니다...");
    Client client = Client.builder().apiKey(apiKey).build();

    try {
        ListModelsConfig config = ListModelsConfig.builder()
                .pageSize(50)
                .build();

        for (com.google.genai.types.Model model : client.models.list(config)) {
            // [수정] Optional에서 문자열을 꺼낸 뒤 비교합니다.
            String name = model.name().orElse("");

            if (name.contains("gemini")) {
                // displayName()도 Optional일 수 있으므로 안전하게 처리
                String display = model.displayName().orElse("설명 없음");
                log.info(">>> 발견된 모델: [{}] / 설명: {}", name, display);
            }
        }

    } catch (Exception e) {
        log.error("모델 리스트 조회 중 에러 발생: {}", e.getMessage());
    }
}
}
