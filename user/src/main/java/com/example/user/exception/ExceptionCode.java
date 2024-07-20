package com.example.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    PHONE_NUMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 번호입니다."),
    PHONE_NUMBER_BLANK(HttpStatus.BAD_REQUEST, "폰 번호를 입력해 주세요."),

    CUSTOMER_NOT_FOUND(HttpStatus.BAD_REQUEST, "고객 계정을 찾을 수 없습니다."),
    PARTNER_NOT_FOUND(HttpStatus.BAD_REQUEST, "파트너 계정을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
