package com.shoppingmall.ecommercebackend.domain.address.entity;

import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import com.shoppingmall.ecommercebackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "address")
public class AddressEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, length = 100)
    private String firstAddress; // 주소(시)

    @Column(nullable = false, length = 100)
    private String secondAddress; // 주소(구)

    @Column(nullable = false, length = 100)
    private String lastAddress; // 주소(마지막)

    @Column(nullable = false, length = 100)
    private String addressDetail; // 상세주소

    @Column(nullable = false, length = 20)
    private String zipCode; // 우편 번호

    @Builder.Default
    @Column(nullable = false)
    private boolean defaultAddress = false; // 기본 배송지 여부(기본값은 false)

    // 기본 배송지 설정 메서드
    public void updateDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}