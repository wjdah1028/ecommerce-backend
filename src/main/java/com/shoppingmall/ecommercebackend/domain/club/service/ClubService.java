package com.shoppingmall.ecommercebackend.domain.club.service;


import com.shoppingmall.ecommercebackend.domain.club.dto.request.ClubRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.club.dto.response.ClubRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.club.dto.response.ClubSearchResponse;
import com.shoppingmall.ecommercebackend.domain.club.entity.ClubEntity;
import com.shoppingmall.ecommercebackend.domain.club.exception.ClubErrorCode;
import com.shoppingmall.ecommercebackend.domain.club.repository.ClubRepository;
import com.shoppingmall.ecommercebackend.domain.league.entity.LeagueEntity;
import com.shoppingmall.ecommercebackend.domain.league.exception.LeagueErrorCode;
import com.shoppingmall.ecommercebackend.domain.league.repository.LeagueRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ClubService {

    private final ClubRepository clubRepository;
    private final LeagueRepository leagueRepository;

    // 구단 등록
    @Transactional
    public ClubRegisterResponse registerClub(ClubRegisterRequest request, Long leagueId) {

        // 리그가 존재하는지 조회
        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new CustomException(LeagueErrorCode.LEAGUE_NOT_FOUND));

        // 구단이 등록됐는지 조회
        if (clubRepository.existsByClubName(request.getClubName())) {
            log.warn("[ClubService] 등록된 구단입니다");
            throw new CustomException(ClubErrorCode.CLUB_DUPLICATE);
        }

        // 구단 객체 생성
        ClubEntity club =
                ClubEntity.builder()
                        .league(league)
                        .clubName(request.getClubName())
                        .build();

        // DB 저장
        ClubEntity savedClub = clubRepository.save(club);

        // 로그 출력
        log.info("[ClubService] 구단 등록 성공: clubName={}", request.getClubName());

        // 응답 세팅
        return ClubRegisterResponse.builder()
                .clubId(savedClub.getClubId())
                .leagueId(savedClub.getLeague().getLeagueId())
                .clubName(savedClub.getClubName())
                .leagueName(savedClub.getLeague().getLeagueName())
                .createdAt(savedClub.getCreatedAt())
                .build();
    }

    // 구단 조회
    public List<ClubSearchResponse> searchClub() {

        // 응답 세팅
        List<ClubSearchResponse> list = new ArrayList<>();
        for (ClubEntity club : clubRepository.findAll()) {
            list.add(ClubSearchResponse.builder()
                    .clubId(club.getClubId())
                    .leagueId(club.getLeague().getLeagueId())
                    .leagueName(club.getLeague().getLeagueName())
                    .clubName(club.getClubName())
                    .build());
        }

        // 로그 출력
        log.info("[ClubService] 구단 조회 성공");

        return list;
    }
}
