package com.shoppingmall.ecommercebackend.domain.uniform.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UniformErrorCode implements BaseErrorCode {

    UNIFORM_DUPLICATE("UF4001", "등록된 유니폼입니다.", HttpStatus.CONFLICT),
    UNIFORM_NOT_FOUND("UF4002", "유니폼을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNIFORM_NOT_AUTHORITY("UF4003", "유니폼 게시글 접근 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
