package com.pingpong.jlab.pingpong.domain.notion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotionResponseDto {
    private List<Page> results;

    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {
        private Properties properties;
    }

    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties {
        @JsonProperty("이름")
        private TitleProperty name;
        @JsonProperty("날짜")
        private DateProperty date;
        @JsonProperty("메모")
        private RichTextProperty memo;
        @JsonProperty("우선순위")
        private NumberProperty priority;
    }

    //Customized Class
    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TitleProperty {
        private List<RichText> title; // 노션은 제목을 리스트로 줍니다.
    }

    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DateProperty {
        private DateData date;
    }

    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RichTextProperty {
        @JsonProperty("rich_text")
        private List<RichText> richText;
    }

    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NumberProperty {
        private Integer number;
    }

    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RichText {
        @JsonProperty("plain_text")
        private String plainText;
    }

    @Getter @NoArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DateData {
        private String start;
    }
}
