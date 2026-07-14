package com.shoppingmall.ecommercebackend.domain.auth.controller;

import com.shoppingmall.ecommercebackend.domain.auth.dto.request.LoginRequest;
import com.shoppingmall.ecommercebackend.domain.auth.dto.response.LoginResponse;
import com.shoppingmall.ecommercebackend.domain.auth.service.AuthService;
import com.shoppingmall.ecommercebackend.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    // 로그인
    @Operation(summary = "로그인 API", description = "사용자가 이메일과 비밀번호로 로그인하는 API")
    @PostMapping("/auth/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        // service 호출
        LoginResponse response = authService.login(request);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "로그인 성공", response));
    }
}
