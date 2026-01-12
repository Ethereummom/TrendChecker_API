package com.pingpong.jlab.pingpong.domain.notion.controller;

import com.pingpong.jlab.pingpong.domain.notion.service.NotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/test/notion")
@RequiredArgsConstructor
public class NotionController {

  private final NotionService notionService;

  @GetMapping("/today")
  public ResponseEntity<String> getTodayNotionTasks() {
    log.info("노션 데이터 추출 테스트 시작...");

    try {
      String result = notionService.fetchTodayTasks();
      log.info("노션 데이터 추출 성공!");
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      log.error("노션 데이터 추출 중 에러 발생: {}", e.getMessage());
      return ResponseEntity.internalServerError().body("에러 발생: " + e.getMessage());
    }
  }

  @GetMapping("/llm")
  public ResponseEntity<String> getTodayNotionTasksWithLLM() {
    log.info("노션 데이터 LLM 답변 생성");
    String result = notionService.returnNotionData();
    return ResponseEntity.ok(result);
  }
}