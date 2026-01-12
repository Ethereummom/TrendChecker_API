package com.pingpong.jlab.pingpong.global.batch.briefing;

import com.pingpong.jlab.pingpong.domain.llm.service.GeminiService;
import com.pingpong.jlab.pingpong.domain.notion.service.NotionService;
import com.pingpong.jlab.pingpong.domain.telegram.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BriefScheduler {

    private final NotionService notionService;
    private final GeminiService geminiService;
    private final TelegramService telegramService;

    /**
     * 매일 아침 08:30:00 자동 실행
     * cron = "초 분 시 일 월 요일"
     * zone = "Asia/Seoul" (서버 시간대 상관없이 한국 시간 기준 실행)
     */
    @Scheduled(cron = "${scheduler.briefing.cron}", zone = "Asia/Seoul")
    public void sendDailyBriefing() {
        log.info(">>> [스케줄러] ☀️ 굿모닝! 아침 브리핑 자동 발송을 시작합니다.");

        try {
            // 1. 노션 데이터 가져오기 (이미 검증된 로직!)
            String rawJson = notionService.fetchTodayTasks();
            String summary = notionService.getExtractedSummary(rawJson);

            // 2. Gemini 요약
            String briefing = geminiService.getDailyBriefing(summary);

            // 3. 텔레그램 전송
            telegramService.sendMessage(briefing);

            log.info(">>> [스케줄러] ✅ 브리핑 전송 완료!");

        } catch (Exception e) {
            log.error(">>> [스케줄러] ❌ 브리핑 전송 실패", e);
            // 에러 나면 나한테 알려주기 (선택 사항)
            telegramService.sendMessage("🚨 비서 시스템 오류 발생: " + e.getMessage());
        }
    }
}
