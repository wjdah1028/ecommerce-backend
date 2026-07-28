package com.shoppingmall.ecommercebackend.domain.uniform.controller;

import com.shoppingmall.ecommercebackend.domain.uniform.dto.request.UniformRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.response.UniformRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.uniform.service.UniformService;
import com.shoppingmall.ecommercebackend.global.common.BaseResponse;
import com.shoppingmall.ecommercebackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Uniform", description = "유니폼 관련 API")
public class UniformController {

    private final UniformService uniformService;

    // 유니폼 등록
    @Operation(summary = "유니폼 등록 API", description = "판매자가 유니폼을 등록하는 API")
    @PostMapping("/uniforms")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<BaseResponse<UniformRegisterResponse>> uniformRegister(
            @Valid @RequestBody UniformRegisterRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // service 호출
        UniformRegisterResponse response = uniformService.uniformRegister(request, userDetails.getUserId());

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "유니폼 등록 성공", response));
    }
}
