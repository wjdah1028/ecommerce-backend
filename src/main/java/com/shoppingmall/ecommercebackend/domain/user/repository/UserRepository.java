package com.shoppingmall.ecommercebackend.domain.user.repository;

import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 이메일로 사용자 조회
    Optional<UserEntity> findByEmail(String email);

    // 이메일이 존재하는지 확인
    boolean existsByEmail(String email);

    // 전화번호가 존재하는지 확인
    boolean existsByPhone(String phone);

    // 닉네임이 존재하는지 조회
    boolean existsByNickname(String nickname);
}
