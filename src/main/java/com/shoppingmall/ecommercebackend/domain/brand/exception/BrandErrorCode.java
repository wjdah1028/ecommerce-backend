package com.shoppingmall.ecommercebackend.domain.brand.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BrandErrorCode implements BaseErrorCode {

    BRAND_DUPLICATE("B4001", "등록된 브랜드입니다.", HttpStatus.CONFLICT),
    BRAND_NOT_FOUND("L4002", "브랜드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
