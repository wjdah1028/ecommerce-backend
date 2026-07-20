package com.shoppingmall.ecommercebackend.domain.league.service;

import com.shoppingmall.ecommercebackend.domain.league.dto.request.LeagueRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.league.dto.response.LeagueRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.league.entity.LeagueEntity;
import com.shoppingmall.ecommercebackend.domain.league.exception.LeagueErrorCode;
import com.shoppingmall.ecommercebackend.domain.league.repository.LeagueRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LeagueService {

    private final LeagueRepository leagueRepository;

    // 리그 등록
    @Transactional
    public LeagueRegisterResponse registerLeague(LeagueRegisterRequest request) {

        // 리그가 중복되는지 조회
        if (leagueRepository.existsByLeagueName(request.getLeagueName())) {
            log.warn("[LeagueService] 등록된 리그입니다: leagueName= {}", request.getLeagueName());
            throw new CustomException(LeagueErrorCode.LEAGUE_DUPLICATE);
        }

        // 리그 객체 생성
        LeagueEntity league =
                LeagueEntity.builder().leagueName(request.getLeagueName()).build();

        // DB 저장
        LeagueEntity savedLeague = leagueRepository.save(league);

        // 로그 출력
        log.info("[LeagueService] 리그 등록 성공: leagueName= {}", league.getLeagueName());

        // 응답 세팅
        return LeagueRegisterResponse.builder()
                .leagueId(savedLeague.getLeagueId())
                .leagueName(savedLeague.getLeagueName())
                .createdAt(savedLeague.getCreatedAt())
                .build();
    }
}
