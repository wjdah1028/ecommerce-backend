package com.shoppingmall.ecommercebackend.domain.club.controller;

import com.shoppingmall.ecommercebackend.domain.club.dto.request.ClubRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.club.dto.response.ClubRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.club.service.ClubService;
import com.shoppingmall.ecommercebackend.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Club", description = "구단 관련 API")
public class ClubController {

    private final ClubService clubService;

    // 구단 등록
    @Operation(summary = "구단 등록 API", description = "관리자가 구단을 등록하는 API")
    @PostMapping("/{league-id}/clubs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<ClubRegisterResponse>> clubRegister(
            @Valid @RequestBody ClubRegisterRequest request,
            @PathVariable("league-id") Long leagueId) {

        // service 호출
        ClubRegisterResponse response = clubService.registerClub(request, leagueId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "구단 등록 성공", response));
    }
}
