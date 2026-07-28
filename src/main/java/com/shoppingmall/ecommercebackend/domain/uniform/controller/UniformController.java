package com.shoppingmall.ecommercebackend.domain.uniform.controller;

import com.shoppingmall.ecommercebackend.domain.uniform.dto.request.UniformRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.request.UniformUpdateRequest;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.response.*;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 유니폼 단건 조회
    @Operation(summary = "유니폼 단건 조회 API", description = "사용자가 로그인 없이 유니폼 정보를 단건 조회하는 API")
    @GetMapping("/uniforms/{uniform-id}")
    public ResponseEntity<BaseResponse<UniformSearchResponse>> uniformSearch(
            @PathVariable("uniform-id") Long uniformId) {

        // service 호출
        UniformSearchResponse response = uniformService.uniformSearch(uniformId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "유니폼 단건 조회 성공", response));
    }

    // 구단별 유니폼 목록 조회
    @Operation(summary = "구단별 유니폼 목록 조회 API", description = "사용자가 구단별 유니폼 목록을 조회하는 API")
    @GetMapping("/clubs/{club-id}/uniforms")
    public ResponseEntity<BaseResponse<List<UniformByClubListResponse>>> uniformByClub(
            @PathVariable("club-id") Long clubId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // service 호출
        List<UniformByClubListResponse> response = uniformService.uniformByClubList(clubId, page - 1, size);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "구단별 유니폼 목록 조회 성공", response));
    }

    // 리그별 유니폼 목록 조회
    @Operation(summary = "리그별 유니폼 목록 조회 API", description = "사용자가 리그별 유니폼 목록을 조회하는 API")
    @GetMapping("/leagues/{league-id}/uniforms")
    public ResponseEntity<BaseResponse<List<UniformByLeagueListResponse>>> uniformByLeague(
            @PathVariable("league-id") Long leagueId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // service 호출
        List<UniformByLeagueListResponse> response = uniformService.uniformByLeagueList(leagueId, page - 1, size);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "리그별 유니폼 목록 조회 성공", response));
    }

    // 유니폼 수정
    @Operation(summary = "유니폼 수정 API", description = "판매자가 유니폼 수정하는 API")
    @PutMapping("/uniforms/{uniform-id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<BaseResponse<UniformUpdateResponse>> uniformUpdate(
            @PathVariable("uniform-id") Long uniformId,
            @Valid @RequestBody UniformUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // service 호출
        UniformUpdateResponse response = uniformService.uniformUpdate(uniformId, request, userDetails.getUserId());

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(200, "유니폼 수정 성공", response));
    }
}
