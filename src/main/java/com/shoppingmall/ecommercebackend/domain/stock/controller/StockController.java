package com.shoppingmall.ecommercebackend.domain.stock.controller;

import com.shoppingmall.ecommercebackend.domain.stock.dto.request.StockRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.stock.dto.response.StockRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.stock.service.StockService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Stock", description = "재고 관련 API")
public class StockController {

    private final StockService stockService;

    // 재고 등록
    @Operation(summary = "재고 등록 API", description = "판매자가 본인이 등록한 유니폼의 재고를 등록하는 API")
    @PostMapping("/{uniform-id}/stocks")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<BaseResponse<StockRegisterResponse>> stockRegister(
            @PathVariable("uniform-id") Long uniformId,
            @Valid @RequestBody StockRegisterRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // service 호출
        StockRegisterResponse response = stockService.stockRegister(request, uniformId, userDetails.getUserId());

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "재고 등록 성공", response));
    }
}
