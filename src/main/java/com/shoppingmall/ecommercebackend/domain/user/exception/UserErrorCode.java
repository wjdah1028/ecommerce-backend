package com.shoppingmall.ecommercebackend.domain.user.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_EMAIL_DUPLICATE("U4001", "중복된 이메일입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("U4002", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    USER_PHONE_DUPLICATE("U4003", "중복된 전화번호입니다.", HttpStatus.CONFLICT),
    USER_NICKNAME_DUPLICATE("U4004", "중복된 닉네임입니다.", HttpStatus.CONFLICT),
    PASSWORD_NOT_MATCH("U4005", "현재 비밀번호가 일치하지 않습니다,", HttpStatus.BAD_REQUEST),
    USER_NICKNAME_SAME("U4006", "변경 전 닉네임과 일치합니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
