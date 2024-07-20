package com.example.reservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "요청한 슬롯에 해당하는 가게를 찾을 수 없습니다."),
    STORE_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "동일한 가게명이 이미 존재합니다."),
    STORE_NAME_BLANK(HttpStatus.BAD_REQUEST, "가게명을 입력해 주세요."),

    CUSTOMER_NOT_FOUND(HttpStatus.BAD_REQUEST, "고객 계정을 찾을 수 없습니다."),

    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "요청한 예약을 찾을 수 없습니다."),
    RESERVATION_CANCELLATION_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "예약 취소가 불가능합니다."),
    RESERVATION_EXPIRED(HttpStatus.BAD_REQUEST, "방문 확인 가능 시간이 지나 예약이 만료되었습니다."),
    RESERVATION_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "방문이 확인되지 않은 예약입니다."),
    OLD_COMPLETED_RESERVATION(HttpStatus.BAD_REQUEST, "방문한 지 일주일이 지난 예약은 리뷰를 작성할 수 없습니다."),
    ALREADY_REVIEWED_RESERVATION(HttpStatus.BAD_REQUEST, "이미 작성한 리뷰가 있습니다."),
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "작성한 리뷰를 찾을 수 없습니다."),

    SLOT_QUANTITY_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "예약 가능한 인원 수를 초과했습니다."),
    SLOT_NOT_FOUND(HttpStatus.BAD_REQUEST, "요청한 슬롯을 찾을 수 없습니다."),
    SLOT_NOT_ALLOWED_FOR_RESERVATION(HttpStatus.BAD_REQUEST, "예약할 수 없는 슬롯입니다."),
    SLOT_NOT_ALLOWED_TO_ADD(HttpStatus.BAD_REQUEST, "새로 추가할 수 없는 슬롯입니다."),
    SLOT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "동일한 날짜 및 시간의 슬롯이 이미 존재합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
