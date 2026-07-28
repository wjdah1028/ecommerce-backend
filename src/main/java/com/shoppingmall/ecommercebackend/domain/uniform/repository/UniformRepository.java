package com.shoppingmall.ecommercebackend.domain.uniform.repository;

import com.shoppingmall.ecommercebackend.domain.club.entity.ClubEntity;
import com.shoppingmall.ecommercebackend.domain.league.entity.LeagueEntity;
import com.shoppingmall.ecommercebackend.domain.uniform.entity.UniformEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniformRepository extends JpaRepository<UniformEntity, Long> {

    // 유니폼 이름이 중복되는지 조회
    boolean existsByUniformName(String uniformName);

    // 구단별 유니폼 조회
    Page<UniformEntity> findAllByClub(ClubEntity club, Pageable pageable);

    // 리그별 유니폼 조회
    Page<UniformEntity> findAllByClub_League(LeagueEntity clubLeague, Pageable pageable);
}
