package com.pingpong.jlab.pingpong.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class NotionConfig {

    @Value("${notion.token}")
    private String notionToken;

    @Bean
    public RestClient notionRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.notion.com/v1")
                .defaultHeader("Authorization", "Bearer " + notionToken)
                .defaultHeader("Notion-Version", "2022-06-28")
                .build();
    }
}
