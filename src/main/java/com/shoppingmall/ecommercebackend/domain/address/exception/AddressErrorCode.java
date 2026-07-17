package com.shoppingmall.ecommercebackend.domain.address.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AddressErrorCode implements BaseErrorCode {

    ADDRESS_DUPLICATE("U4001", "중복된 주소입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
