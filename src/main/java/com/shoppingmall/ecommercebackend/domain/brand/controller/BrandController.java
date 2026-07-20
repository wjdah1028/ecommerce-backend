package com.shoppingmall.ecommercebackend.domain.brand.controller;

import com.shoppingmall.ecommercebackend.domain.brand.dto.request.BrandRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.brand.dto.request.BrandUpdateRequest;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandSearchResponse;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandUpdateResponse;
import com.shoppingmall.ecommercebackend.domain.brand.service.BrandService;
import com.shoppingmall.ecommercebackend.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 브랜드 조회
    @Operation(summary = "브랜드 목록 조회 API", description = "사용자가 로그인 없이 브랜드 목록을 조회하는 API")
    @GetMapping("/brands")
    public ResponseEntity<BaseResponse<List<BrandSearchResponse>>> brandList() {

        // service 호출
        List<BrandSearchResponse> response = brandService.searchBrand();

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "브랜드 목록 조회 성공", response));
    }

    // 브랜드 수정
    @Operation(summary = "브랜드 수정 API", description = "관리자가 브랜드를 수정하는 API")
    @PutMapping("/brands/{brand-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<BrandUpdateResponse>> brandUpdate(
            @PathVariable("brand-id") Long brandId,
            @Valid @RequestBody BrandUpdateRequest request) {

        // service 호출
        BrandUpdateResponse response = brandService.updateBrand(brandId, request);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "브랜드 수정 성공", response));
    }

    // 브랜드 삭제
    @Operation(summary = "브랜드 삭제 API", description = "관리자가 브랜드를 삭제하는 API")
    @DeleteMapping("/brands/{brand-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> deleteBrand(
            @PathVariable("brand-id") Long brandId) {

        // service 호출
        brandService.deleteBrand(brandId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "브랜드 삭제 성공", null));
    }
}
