package com.pingpong.jlab.pingpong.domain.notion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingpong.jlab.pingpong.domain.llm.service.GeminiService;
import com.pingpong.jlab.pingpong.domain.telegram.service.TelegramService;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotionService {

    private final RestClient notionRestClient;
    private final GeminiService geminiService;
    private final TelegramService telegramService;

    @Value("${notion.database-id}")
    private String databaseId;

    public String fetchTodayTasks() {
    // 1. [전략 수정] "어제" 날짜를 구합니다. (UTC 시차 때문에 어제 데이터로 잡힐 수 있으므로)
    String yesterday = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1).toString();

    log.info(">>> 노션 검색 범위 시작일: {}", yesterday); // 2026-01-10

    return notionRestClient.post()
            .uri("/databases/{databaseId}/query", databaseId)
            .body(java.util.Map.of(
                "filter", java.util.Map.of(
                    "property", "날짜",
                    "date", java.util.Map.of("on_or_after", yesterday) // [핵심] equals 대신 "이 날짜 이후"로 검색
                ),
                "sorts", java.util.List.of( // 기왕이면 시간순 정렬도 추가
                    java.util.Map.of(
                        "property", "날짜",
                        "direction", "ascending"
                    )
                )
            ))
            .retrieve()
            .body(String.class);
    }

    public String getExtractedSummary(String jsonResponse) {
    try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);
        JsonNode results = root.path("results");

        // 1. 오늘 날짜 (KST) 구하기 ("2026-01-11" 형태)
        String todayKst = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();

        StringBuilder summary = new StringBuilder();

        for (JsonNode page : results) {
            // [A] 제목 추출 로직 (properties -> 이름 -> title -> 0 -> plain_text)
            String title = "제목 없음";
            JsonNode titleNode = page.path("properties").path("이름").path("title");

            if (titleNode.isArray() && !titleNode.isEmpty()) {
                title = titleNode.get(0).path("plain_text").asText();
            }

            // [B] 날짜 추출 및 필터링 로직
            JsonNode dateNode = page.path("properties").path("날짜").path("date");

            if (!dateNode.isMissingNode()) {
                String start = dateNode.path("start").asText();
                // 예: "2026-01-11T01:15:00+09:00" (시간 있음) 또는 "2026-01-11" (시간 없음)

                // [핵심 필터링] 시작 시간이 '오늘 날짜(KST)'로 시작하는지 문자열로 확인
                if (start.startsWith(todayKst)) {
                    String timeInfo = "";
                    if (start.contains("T")) {
                        // "T"가 있으면 시간 정보가 있는 것 -> HH:mm 추출
                        timeInfo = start.substring(11, 16);
                    } else {
                        timeInfo = "하루 종일";
                    }
                    summary.append(String.format("- %s (%s)\n", title, timeInfo));
                }
            }
        }

        // 반복문이 끝났는데 요약된 내용이 없으면 일정이 없는 것
        if (summary.length() == 0) {
            return "오늘은 예정된 일정이 없습니다.";
        }

        return summary.toString();

    } catch (Exception e) {
        // 에러 로그를 남기고 런타임 예외로 던짐 (스케줄러나 컨트롤러에서 잡을 수 있게)
        throw new RuntimeException("노션 데이터 파싱 중 오류 발생: " + e.getMessage(), e);
    }
}

    public String returnNotionData() {
      String returnMsg = geminiService.getDailyBriefing(fetchTodayTasks());
      telegramService.sendMessage(returnMsg);
      return returnMsg;
    }
}
