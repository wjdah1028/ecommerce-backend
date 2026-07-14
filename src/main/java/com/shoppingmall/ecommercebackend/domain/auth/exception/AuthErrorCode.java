package com.shoppingmall.ecommercebackend.domain.auth.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    EMAIL_FAIL("A4001", "존재하지 않는 이메일입니다.", HttpStatus.NOT_FOUND),
    PASSWORD_FAIL("A4002", "비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
