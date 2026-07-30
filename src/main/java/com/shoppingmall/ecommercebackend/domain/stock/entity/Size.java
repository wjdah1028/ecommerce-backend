package com.shoppingmall.ecommercebackend.domain.stock.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Size {

    @Schema(description = "S 사이즈")
    S,

    @Schema(description = "M 사이즈")
    M,

    @Schema(description = "L 사이즈")
    L,

    @Schema(description = "XL 사이즈")
    XL,

    @Schema(description = "XXL 사이즈")
    XXL,

    @Schema(description = "XXXL 사이즈")
    XXXL;
}
