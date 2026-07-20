package com.shoppingmall.ecommercebackend.domain.brand.repository;

import com.shoppingmall.ecommercebackend.domain.brand.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    // 등록된 브랜드 이름이 존재하는지 조회
    boolean existsByBrandName(String brandName);
}
