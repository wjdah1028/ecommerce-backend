package com.shoppingmall.ecommercebackend.domain.user.repository;

import com.shoppingmall.ecommercebackend.domain.user.entity.SocialAccountEntity;
import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccountEntity, Long> {

    // 기존 회원인지 조회
    Optional<SocialAccountEntity> findByLoginProviderAndSocialUserId(String loginProvider, String socialUserId);

    // UserEntity와 연관된 SocialAccountEntity 삭제
    void deleteByUser(UserEntity user);
}
