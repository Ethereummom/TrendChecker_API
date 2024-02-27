package com.pingpong.jlab.pingpong.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력 값입니다."),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 타입입니다."),
	MISSING_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C003", "입력된 인자의 수가 부족합니다."),
	NOT_EXIST_API(HttpStatus.BAD_REQUEST, "C004", "요청 API 주소가 올바르지 않습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C005", "지원되지 않는 메서드입니다."),
	HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "접근 권한이 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C007", "서버 에러입니다."),

    // User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
	USER_INVALID(HttpStatus.BAD_REQUEST, "U002", "권한이 없는 사용자입니다."),
	USER_STILL_NOT_SUBSCRIBED(HttpStatus.OK, "U003" , "구독중이지 않은 전략입니다."),
	USER_STILL_NOT_RECOMMENDED(HttpStatus.OK, "U004", "추천하지 않은 전략입니다."),
	USER_ALREADY_SUBSCRIBED(HttpStatus.OK, "U004" , "이미 구독중인 전략입니다."),
	USER_ALREADY_RECOMMENDED(HttpStatus.OK, "U005" , "이미 추쳔하셨습니다."),


	// Token
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A001", "존재하지 않는 리프레쉬 토큰입니다."),
	TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A003", "만료된 토큰입니다."),

	//Entity
	ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "E001" , "존재하지 않는 엔티티입니다.");

    private final HttpStatus status;
	private final String code;
	private final String message;

    ErrorCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
    
}
