package com.shoppingmall.ecommercebackend.domain.league.repository;

import com.shoppingmall.ecommercebackend.domain.league.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {

    // 등록된 리그 이름이 존재하는지 조회
    boolean existsByLeagueName(String leagueName);

    LeagueEntity findByLeagueName(String leagueName);
}
