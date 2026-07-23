package com.shoppingmall.ecommercebackend.domain.club.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ClubErrorCode implements BaseErrorCode {

    CLUB_DUPLICATE("C4001", "등록된 구단입니다.", HttpStatus.CONFLICT),
    CLUB_NOT_FOUND("C4002", "구단을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
