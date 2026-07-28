package com.shoppingmall.ecommercebackend.domain.uniform.repository;

import com.shoppingmall.ecommercebackend.domain.uniform.entity.UniformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniformRepository extends JpaRepository<UniformEntity, Long> {

    // 유니폼 이름이 중복되는지 조회
    boolean existsByUniformName(String uniformName);
}
