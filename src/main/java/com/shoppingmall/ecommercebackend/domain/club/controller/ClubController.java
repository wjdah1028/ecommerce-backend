package com.shoppingmall.ecommercebackend.domain.club.controller;

import com.shoppingmall.ecommercebackend.domain.club.dto.request.ClubRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.club.dto.request.ClubUpdateRequest;
import com.shoppingmall.ecommercebackend.domain.club.dto.response.ClubRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.club.dto.response.ClubSearchResponse;
import com.shoppingmall.ecommercebackend.domain.club.dto.response.ClubUpdateResponse;
import com.shoppingmall.ecommercebackend.domain.club.dto.response.LeagueByClubSearchResponse;
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

import java.util.List;

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

    // 구단 조회
    @Operation(summary = "구단 조회 API", description = "로그인 필요 없이 모든 사용자가 구단을 조회하는 API")
    @GetMapping("/clubs")
    public ResponseEntity<BaseResponse<List<ClubSearchResponse>>> clubSearch() {

        // service 호출
        List<ClubSearchResponse> response = clubService.searchClub();

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "구단 조회 성공", response));
    }

    // 리그별 구단 조회
    @Operation(summary = "리그별 구단 조회 API", description = "로그인 필요 없이 모든 사용자가 리그별로 구단을 조회하는 API")
    @GetMapping("/{league-id}/clubs")
    public ResponseEntity<BaseResponse<List<LeagueByClubSearchResponse>>> leagueByClubSearch(
            @PathVariable("league-id") Long leagueId) {

        // service 호출
        List<LeagueByClubSearchResponse> response = clubService.searchLeagueByClub(leagueId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "리그별 구단 조회 성공", response));
    }

    // 구단 수정
    @Operation(summary = "구단 수정 API", description = "관리자가 리그와 구단 이름을 수정하는 API")
    @PutMapping("/clubs/{club-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<ClubUpdateResponse>> updateClub(
            @PathVariable("club-id") Long clubId,
            @Valid @RequestBody ClubUpdateRequest request) {

        // service 호출
        ClubUpdateResponse response = clubService.updateClub(request,clubId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "구단 수정 성공", response));
    }
}
