package com.architecture.controller;

import com.architecture.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionC {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> ExceptionHandler(Exception e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ErrorResponseDto>  AccessDeniedExceptionHandler(AccessDeniedException e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }
}
