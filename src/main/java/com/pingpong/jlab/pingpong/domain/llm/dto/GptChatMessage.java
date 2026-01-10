package com.pingpong.jlab.pingpong.domain.llm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GptChatMessage {

  @Schema(description = "역할", example = "")
  private String role;

  @Schema(description = "컨텐츠", example = "")
  private String content;

  public GptChatMessage(String role, String messages) {
    this.role = role;
    this.content = messages;
  }

}
