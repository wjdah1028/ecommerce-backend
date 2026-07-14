package com.shoppingmall.ecommercebackend.domain.user.controller;

import com.shoppingmall.ecommercebackend.domain.user.dto.request.SignUpRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.response.SignUpResponse;
import com.shoppingmall.ecommercebackend.domain.user.service.UserService;
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
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    // 회원가입
    @Operation(summary = "회원가입 API", description = "사용자가 이름, 이메일, 비밀번호, 전화번호, 닉네임을 넣어 회원가입하는 API")
    @PostMapping("/users")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(
            @Valid @RequestBody SignUpRequest request) {

        // service 호출
        SignUpResponse response = userService.signUp(request);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "회원가입 성공", response));
    }
}
