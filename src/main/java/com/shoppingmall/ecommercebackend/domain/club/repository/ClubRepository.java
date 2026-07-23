package com.shoppingmall.ecommercebackend.domain.club.repository;

import com.shoppingmall.ecommercebackend.domain.club.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    // 등록된 구단 이름이 존재하는지 조회
    boolean existsByClubName(String clubName);
}
