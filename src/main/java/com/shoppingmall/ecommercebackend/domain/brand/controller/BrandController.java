package com.shoppingmall.ecommercebackend.domain.brand.controller;

import com.shoppingmall.ecommercebackend.domain.brand.dto.request.BrandRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.brand.service.BrandService;
import com.shoppingmall.ecommercebackend.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Brand", description = "브랜드 관련 API")
public class BrandController {

    private final BrandService brandService;

    // 브랜드 등록
    @Operation(summary = "브랜드 등록 API", description = "관리자가 브랜드를 등록하는 API")
    @PostMapping("/brands")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<BrandRegisterResponse>> brandRegister(
            @Valid @RequestBody BrandRegisterRequest request) {

        // service 호출
        BrandRegisterResponse response = brandService.registerBrand(request);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "브랜드 등록 성공", response));
    }
}
