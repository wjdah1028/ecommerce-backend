package com.shoppingmall.ecommercebackend.domain.address.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AddressErrorCode implements BaseErrorCode {

    ADDRESS_DUPLICATE("A4001", "중복된 주소입니다.", HttpStatus.CONFLICT),
    ADDRESS_NOT_FOUND("A4002", "존재하지 않는 주소입니다.", HttpStatus.NOT_FOUND),
    DEFAULT_ADDRESS_REGISTER("A4003", "등록된 기본 배송지입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
