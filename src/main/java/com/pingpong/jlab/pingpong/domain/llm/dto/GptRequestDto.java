package com.pingpong.jlab.pingpong.domain.llm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * CHATGpt Prompt Request DTO
 * */
@Getter
@Setter
@NoArgsConstructor
public class GptRequestDto {

  @Schema(description = "프롬프트(입력 명령어)", example = "")
  public String prompt;

  @Schema(description = "요청 유저 아이디", example = "user1")
  public String reqUserId;

  @Schema(description = "요청 타입", example = "1 - 주식, 2 - 코인, 3 - 부동산, 4 - 정치")
  public String reqType;

  @Schema(description = "요청 시간", example = "2025-05-15-14:00:14")
  public LocalDateTime reqTime;

}
