package com.example.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(final CustomException customException) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(customException.getMessage(), customException.getExceptionCode()));

    }

    @AllArgsConstructor
    @Getter
    public static class ExceptionResponse {
        private String message;
        private ExceptionCode exceptionCode;
    }
}
