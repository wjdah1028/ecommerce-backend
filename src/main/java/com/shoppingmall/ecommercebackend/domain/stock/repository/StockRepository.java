package com.shoppingmall.ecommercebackend.domain.stock.repository;

import com.shoppingmall.ecommercebackend.domain.stock.entity.Size;
import com.shoppingmall.ecommercebackend.domain.stock.entity.StockEntity;
import com.shoppingmall.ecommercebackend.domain.uniform.entity.UniformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

    // 특정 유니폼과 사이즈 재고가 존재하는지 확인
    boolean existsByUniformAndSize(UniformEntity uniform, Size size);

    // 특정 유니폼과 사이즈로 재고 조회
    Optional<StockEntity> findByUniformAndSize(UniformEntity uniform, Size size);
}
