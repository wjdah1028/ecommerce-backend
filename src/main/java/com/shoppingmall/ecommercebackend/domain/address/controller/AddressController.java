package com.shoppingmall.ecommercebackend.domain.address.controller;

import com.shoppingmall.ecommercebackend.domain.address.dto.request.AddressRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.address.dto.response.AddressRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.address.sevice.AddressService;
import com.shoppingmall.ecommercebackend.global.common.BaseResponse;
import com.shoppingmall.ecommercebackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Address", description = "주소 관련 API")
public class AddressController {

    private final AddressService addressService;

    // 주소 등록
    @Operation(summary = "주소 등록 API", description = "기본 배송지 설정을 제외한 전체 주소, 우편번호를 등록하는 API(주소를 처음 등록할때는 기본 배송지 여부는 false)")
    @PostMapping("/addresses")
    public ResponseEntity<BaseResponse<AddressRegisterResponse>> addressRegister(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AddressRegisterRequest request) {

        // service 호출
        AddressRegisterResponse response = addressService.registerAddress(request, userDetails.getUserId());

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "주소 등록 성공", response));
    }
}
