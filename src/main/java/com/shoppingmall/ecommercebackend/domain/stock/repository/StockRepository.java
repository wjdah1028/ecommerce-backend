package com.shoppingmall.ecommercebackend.domain.stock.repository;

import com.shoppingmall.ecommercebackend.domain.stock.entity.Size;
import com.shoppingmall.ecommercebackend.domain.stock.entity.StockEntity;
import com.shoppingmall.ecommercebackend.domain.uniform.entity.UniformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

    // 특정 유니폼과 사이즈로 재고 조회
    Optional<StockEntity> findByUniformAndSize(UniformEntity uniform, Size size);

    // 특정 유니폼 재고 목록 전체 조회
    List<StockEntity> findAllByUniform(UniformEntity uniform);
}
