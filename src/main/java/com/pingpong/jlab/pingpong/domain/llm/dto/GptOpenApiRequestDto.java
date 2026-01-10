package com.pingpong.jlab.pingpong.domain.llm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GptOpenApiRequestDto {

  @Schema(description = "사용 할 GPT 모델명", example = "CHATGPT 3.5 turbo")
  private String model;

  @Schema(description = "메시지", example = "")
  private List<GptChatMessage> messages;

  @Schema(description = "실시간여부", example = "")
  private boolean stream;

  public GptOpenApiRequestDto(String model, List<GptChatMessage> messages, boolean stream){

    this.model = model;
    this.messages = messages;
    this.stream = stream;

  }

}
