package com.shoppingmall.ecommercebackend.domain.user.controller;

import com.shoppingmall.ecommercebackend.domain.user.dto.request.SignUpRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.response.SignUpResponse;
import com.shoppingmall.ecommercebackend.domain.user.dto.response.UserInfoResponse;
import com.shoppingmall.ecommercebackend.domain.user.service.UserService;
import com.shoppingmall.ecommercebackend.global.common.BaseResponse;
import com.shoppingmall.ecommercebackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    // 내 정보 조회
    @Operation(summary = "내 정보 조회 API", description = "사용자의 id를 가지고 사용자 정보를 조회하는 API")
    @GetMapping("/users/me")
    public ResponseEntity<BaseResponse<UserInfoResponse>> userInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // service 호출
        UserInfoResponse response = userService.userInfo(userDetails.getUserId());

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "내 정보 조회 성공", response));
    }
}
