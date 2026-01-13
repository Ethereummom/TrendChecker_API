package com.pingpong.jlab.pingpong.domain.telegram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TelegramService {

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.chatId}")
    private String chatId;

    @Value("${telegram.url}")
    private String telegramURL;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String message) {
        String url = telegramURL + botToken + "/sendMessage";

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("chat_id", chatId);
            body.put("text", message);
            // body.put("parse_mode", "Markdown"); // 필요시 마크다운 활성화 가능

            restTemplate.postForObject(url, body, String.class);
            log.info("텔레그램 메시지 전송 성공!");
        } catch (Exception e) {
            log.error("텔레그램 전송 실패: {}", e.getMessage());
        }
    }
}
