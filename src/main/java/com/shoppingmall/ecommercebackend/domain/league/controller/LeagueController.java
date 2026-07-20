package com.shoppingmall.ecommercebackend.domain.league.controller;

import com.shoppingmall.ecommercebackend.domain.league.dto.request.LeagueRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.league.dto.response.LeagueRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.league.service.LeagueService;
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
@Tag(name = "League", description = "리그 관련 API")
public class LeagueController {

    private final LeagueService leagueService;

    // 리그 등록
    @Operation(summary = "리그 등록 API", description = "관리자가 리그 등록하는 API")
    @PostMapping("/league")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<LeagueRegisterResponse>> leagueRegister(
            @Valid @RequestBody LeagueRegisterRequest request) {

        // service 호출
        LeagueRegisterResponse response = leagueService.registerLeague(request);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "리그 등록 성공", response));
    }
}
