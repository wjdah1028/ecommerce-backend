package com.shoppingmall.ecommercebackend.domain.stock.controller;

import com.shoppingmall.ecommercebackend.domain.stock.dto.request.StockRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.stock.dto.request.StockUpdateRequest;
import com.shoppingmall.ecommercebackend.domain.stock.dto.response.StockAllCountResponse;
import com.shoppingmall.ecommercebackend.domain.stock.dto.response.StockRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.stock.dto.response.StockUpdateResponse;
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

import java.util.List;

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

    // 재고 전체 목록 조회
    @Operation(summary = "재고 목록 전체 조회 API", description = "특정 유니폼 재고 목록 전체 조회하는 API")
    @GetMapping("/{uniform-id}/stocks")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<BaseResponse<List<StockAllCountResponse>>> stockAllCount(
            @PathVariable("uniform-id") Long uniformId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // service 호출
        List<StockAllCountResponse> response = stockService.stockAllCount(uniformId, userDetails.getUserId());

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "특정 유니폼 전체 재고 목록 조회 성공", response));
    }

    // 재고 수정
    @Operation(summary = "재고 수정 API", description = "판매자가 등록한 유니폼의 재고를 수정하는 API")
    @PutMapping("/{uniform-id}/stocks/{stock-id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<BaseResponse<StockUpdateResponse>> stockUpdate(
            @Valid @RequestBody StockUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("uniform-id") Long uniformId,
            @PathVariable("stock-id") Long stockId) {

        // service 호출
        StockUpdateResponse response = stockService.stockUpdate(request, uniformId, userDetails.getUserId(), stockId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "재고 수정 성공", response));
    }

    // 재고 삭제
    @Operation(summary = "재고 삭제 API", description = "판매자가 등록한 유니폼 재고를 삭제하는 API")
    @DeleteMapping("/{uniform-id}/stocks/{stock-id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<BaseResponse<Void>> deleteStock(
            @PathVariable("stock-id") Long stockId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("uniform-id") Long uniformId) {

        // service 호출
        stockService.deleteStock(stockId, userDetails.getUserId(), uniformId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "재고 삭제 성공", null));
    }
}
