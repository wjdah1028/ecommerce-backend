package com.shoppingmall.ecommercebackend.domain.address.repository;

import com.shoppingmall.ecommercebackend.domain.address.entity.AddressEntity;
import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    // 우편번호와 상세 주소로 조회
    boolean existsByZipCodeAndAddressDetail(String zipCode, String addressDetail);

    // 기본 배송지 조회
    Optional<AddressEntity> findByUserAndDefaultAddress(UserEntity user, boolean defaultAddress);

    // 사용자 주소 전체 목록
    List<AddressEntity> findAllByUser(UserEntity user);
}
