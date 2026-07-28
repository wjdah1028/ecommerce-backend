package com.shoppingmall.ecommercebackend.domain.uniform.service;

import com.shoppingmall.ecommercebackend.domain.brand.entity.BrandEntity;
import com.shoppingmall.ecommercebackend.domain.brand.exception.BrandErrorCode;
import com.shoppingmall.ecommercebackend.domain.brand.repository.BrandRepository;
import com.shoppingmall.ecommercebackend.domain.club.entity.ClubEntity;
import com.shoppingmall.ecommercebackend.domain.club.exception.ClubErrorCode;
import com.shoppingmall.ecommercebackend.domain.club.repository.ClubRepository;
import com.shoppingmall.ecommercebackend.domain.league.entity.LeagueEntity;
import com.shoppingmall.ecommercebackend.domain.league.exception.LeagueErrorCode;
import com.shoppingmall.ecommercebackend.domain.league.repository.LeagueRepository;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.request.UniformRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.response.UniformByClubListResponse;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.response.UniformByLeagueListResponse;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.response.UniformRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.uniform.dto.response.UniformSearchResponse;
import com.shoppingmall.ecommercebackend.domain.uniform.entity.UniformEntity;
import com.shoppingmall.ecommercebackend.domain.uniform.exception.UniformErrorCode;
import com.shoppingmall.ecommercebackend.domain.uniform.repository.UniformRepository;
import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import com.shoppingmall.ecommercebackend.domain.user.exception.UserErrorCode;
import com.shoppingmall.ecommercebackend.domain.user.repository.UserRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UniformService {

    private final UniformRepository uniformRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final BrandRepository brandRepository;
    private final LeagueRepository leagueRepository;

    // 유니폼 등록
    @Transactional
    public UniformRegisterResponse uniformRegister(UniformRegisterRequest request, Long userId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 브랜드가 존재하는지 조회
        BrandEntity brandEntity = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new CustomException(BrandErrorCode.BRAND_NOT_FOUND));

        // 구단이 존재하는지 조회
        ClubEntity clubEntity = clubRepository.findById(request.getClubId())
                .orElseThrow(() -> new CustomException(ClubErrorCode.CLUB_NOT_FOUND));

        // 유니폼이 등록됐는지 조회
        if (uniformRepository.existsByUniformName(request.getUniformName())) {
            log.warn("[UniformService] 등록된 유니폼 입니다: uniformName= {}", request.getUniformName());
            throw new CustomException(UniformErrorCode.UNIFORM_DUPLICATE);
        }

        // 유니폼 객체 생성
        UniformEntity uniform = UniformEntity.builder()
                .uniformName(request.getUniformName())
                .brand(brandEntity)
                .club(clubEntity)
                .user(user)
                .uniformImage(request.getUniformImage())
                .price(request.getPrice())
                .build();

        // DB 저장
        uniformRepository.save(uniform);

        // 로그 출력
        log.info("[UniformService] 유니폼 등록에 성공했습니다: uniformName= {}", uniform.getUniformName());

        // 응답 세팅
        return UniformRegisterResponse.builder()
                .uniformId(uniform.getUniformId())
                .uniformName(uniform.getUniformName())
                .brandId(uniform.getBrand().getBrandId())
                .brandName(uniform.getBrand().getBrandName())
                .clubId(uniform.getClub().getClubId())
                .clubName(uniform.getClub().getClubName())
                .leagueId(uniform.getClub().getLeague().getLeagueId())
                .leagueName(uniform.getClub().getLeague().getLeagueName())
                .uniformImage(uniform.getUniformImage())
                .price(uniform.getPrice())
                .createdAt(uniform.getCreatedAt())
                .build();
    }

    // 유니폼 단건 조회
    public UniformSearchResponse uniformSearch(Long uniformId) {

        // 유니폼이 존재하는지 조회
        UniformEntity uniform = uniformRepository.findById(uniformId)
                .orElseThrow(() -> new CustomException(UniformErrorCode.UNIFORM_NOT_FOUND));

        // 로그 출력
        log.info("[UniformService] 유니폼 단건 조회 성공: uniformId= {}", uniform.getUniformId());

        // 응답 세팅
        return UniformSearchResponse.builder()
                .uniformId(uniform.getUniformId())
                .uniformName(uniform.getUniformName())
                .uniformImage(uniform.getUniformImage())
                .price(uniform.getPrice())
                .brandName(uniform.getBrand().getBrandName())
                .clubName(uniform.getClub().getClubName())
                .leagueName(uniform.getClub().getLeague().getLeagueName())
                .build();
    }

    // 구단별 유니폼 전체 조회
    public List<UniformByClubListResponse> uniformByClubList(Long clubId, int page, int size) {

        // 구단이 존재하는지 조회
        ClubEntity club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ClubErrorCode.CLUB_NOT_FOUND));

        // 응답 세팅
        List<UniformByClubListResponse> list = new ArrayList<>();
        for (UniformEntity uniform : uniformRepository.findAllByClub(club, PageRequest.of(page, size)).getContent()) {
            list.add(UniformByClubListResponse.builder()
                    .clubId(uniform.getClub().getClubId())
                    .clubName(uniform.getClub().getClubName())
                    .uniformId(uniform.getUniformId())
                    .uniformName(uniform.getUniformName())
                    .uniformImage(uniform.getUniformImage())
                    .price(uniform.getPrice())
                    .build());
        }

        // 로그 출력
        log.info("[UniformService] 구단별 유니폼 목록 전체 조회 성공");

        return list;
    }

    // 리그별 유니폼 전체 조회
    public List<UniformByLeagueListResponse> uniformByLeagueList(Long leagueId, int page, int size) {

        // 리그가 존재하는지 조회
        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new CustomException(LeagueErrorCode.LEAGUE_NOT_FOUND));

        // 응답 세팅
        List<UniformByLeagueListResponse> list = new ArrayList<>();
        for (UniformEntity uniform : uniformRepository.findAllByClub_League(league, PageRequest.of(page, size)).getContent()) {
            list.add(UniformByLeagueListResponse.builder()
                    .leagueId(uniform.getClub().getLeague().getLeagueId())
                    .leagueName(uniform.getClub().getLeague().getLeagueName())
                    .uniformId(uniform.getUniformId())
                    .uniformName(uniform.getUniformName())
                    .uniformImage(uniform.getUniformImage())
                    .price(uniform.getPrice())
                    .build());
        }

        // 로그 출력
        log.info("[UniformService] 리그별 유니폼 목록 조회 성공");

        return list;
    }
}
