package com.pingpong.jlab.pingpong.domain.llm.service;

import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import org.springframework.beans.factory.annotation.Value;


/**
 * GPT 공통 응답 처리 Service
 *
 */
public interface GptService {

  /** OPENAI RESOURCES INFO */
  @Value("${openai.key}")
  public String OPENAI_API_KEY = null;

  public static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";


//  /**
//   * 결과 처리
//   *
//   * @param <T> 응답 CLASS TYPE
//   * @param code 응답 코드
//   * @param arguments 메시지 변수
//   * @param body 응답 값
//   * @return 응답 정보
//   */
//  <T> ApiResponse <T> response (String code, String[] arguments, T body);
//
//  /**
//   * 결과 정상 처리
//   * <pre>Code: I00001</pre>
//   *
//   * @param <T> 응답 CLASS TYPE
//   * @return 응답 정보
//   */
//  default <T> ApiResponse<T> ok() {
//    return ok("haha");
//  }
//
//  /**
//   * 결과 정상 처리
//   *
//   * @param <T> 응답 CLASS TYPE
//   * @param code 정상 응답 코드
//   * @return 응답 정보
//   */
//  default <T> ApiResponse<T> ok(String code) {
//    return response(code, null, null);
//  }

  /**
   * GPT PROMPT
   *
   * @param <T> 응답 CLASS TYPE
   * @return 응답 정보
   */
  ApiResponse getGptData(String prompt, String type);


}
