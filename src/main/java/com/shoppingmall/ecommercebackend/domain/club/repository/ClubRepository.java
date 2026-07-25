package com.shoppingmall.ecommercebackend.domain.club.repository;

import com.shoppingmall.ecommercebackend.domain.club.entity.ClubEntity;
import com.shoppingmall.ecommercebackend.domain.league.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    // 등록된 구단 이름이 존재하는지 조회
    boolean existsByClubName(String clubName);

    // 리그 별 구단 조회
    List<ClubEntity> findAllByLeague(LeagueEntity league);

    // 리그가 삭제될때 구단도 삭제
    void deleteByLeague(LeagueEntity league);
}
