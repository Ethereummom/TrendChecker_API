package com.pingpong.jlab.pingpong.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.pingpong.jlab.pingpong.global.error.exception.BusinessException;
import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController

public class GlobalExceptionHandler {

    //Business Error
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException ex){
        log.error("handleEntityNotFoundException", ex);

        final ErrorCode errorCode = ex.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    //Binding Error (param)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMEthodArgumentNotValidException(MethodArgumentNotValidException manvex){
        log.info("MethodArgumentNotValidException : {}", manvex.getMessage());

        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }   

    //Input Args Count Invalid or Less
    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestValueException(MissingRequestValueException mrvex){
        log.info("MissingRequestValueException : {}",mrvex.getMessage());

        final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //Request to a Non - Existent API endpoint

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException nhfex){
        log.info("NoHandlerFoundException : {}" , nhfex.getMessage());

        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_EXIST_API);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
}
