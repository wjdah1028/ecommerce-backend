package com.shoppingmall.ecommercebackend.domain.league.exception;

import com.shoppingmall.ecommercebackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LeagueErrorCode implements BaseErrorCode {

    LEAGUE_DUPLICATE("L4001", "등록된 리그입니다.", HttpStatus.CONFLICT),
    LEAGUE_NOT_FOUND("L4002", "리그를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
