package com.shoppingmall.ecommercebackend.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {

    @Schema(description = "구매자(사용자)")
    BUYER,

    @Schema(description = "판매자")
    SELLER,

    @Schema(description = "관리자")
    ADMIN;
}
