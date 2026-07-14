package com.shoppingmall.ecommercebackend.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "로그인 응답 DTO", description = "사용자가 로그인할때 서버에서 반환하는 데이터")
public class LoginResponse {

    @Schema(description = "Access Token")
    private String accessToken;
}
