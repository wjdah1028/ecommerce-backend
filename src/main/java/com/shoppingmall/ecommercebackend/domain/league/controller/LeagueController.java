package com.shoppingmall.ecommercebackend.domain.league.controller;

import com.shoppingmall.ecommercebackend.domain.league.dto.request.LeagueRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.league.dto.request.LeagueUpdateRequest;
import com.shoppingmall.ecommercebackend.domain.league.dto.response.LeagueRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.league.dto.response.LeagueSearchResponse;
import com.shoppingmall.ecommercebackend.domain.league.dto.response.LeagueUpdateResponse;
import com.shoppingmall.ecommercebackend.domain.league.service.LeagueService;
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
@Tag(name = "League", description = "리그 관련 API")
public class LeagueController {

    private final LeagueService leagueService;

    // 리그 등록
    @Operation(summary = "리그 등록 API", description = "관리자가 리그 등록하는 API")
    @PostMapping("/leagues")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<LeagueRegisterResponse>> leagueRegister(
            @Valid @RequestBody LeagueRegisterRequest request) {

        // service 호출
        LeagueRegisterResponse response = leagueService.registerLeague(request);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "리그 등록 성공", response));
    }

    // 리그 목록 조회
    @Operation(summary = "리그 목록 조회 API", description = "사용자가 로그인 필요 없이 리그 목록 조회하는 API")
    @GetMapping("/leagues")
    public ResponseEntity<BaseResponse<List<LeagueSearchResponse>>> leagueSearch() {

        // service 호출
        List<LeagueSearchResponse> response = leagueService.searchLeague();

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "리그 목록 조회 성공", response));
    }

    // 리그 수정
    @Operation(summary = "리그 수정 API", description = "관리자가 리그 이름을 수정하는 API")
    @PutMapping("/leagues/{league-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<LeagueUpdateResponse>> leagueUpdate(
            @Valid @RequestBody LeagueUpdateRequest request,
            @PathVariable("league-id") Long leagueId) {

        // service 호출
        LeagueUpdateResponse response = leagueService.updateLeague(leagueId, request);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "리그 수정 성공", response));
    }
}
