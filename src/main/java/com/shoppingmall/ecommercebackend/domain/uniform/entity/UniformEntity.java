package com.shoppingmall.ecommercebackend.domain.uniform.entity;

import com.shoppingmall.ecommercebackend.domain.brand.entity.BrandEntity;
import com.shoppingmall.ecommercebackend.domain.club.entity.ClubEntity;
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
@Table(name = "uniforms")
public class UniformEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uniformId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private ClubEntity club;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 300)
    private String uniformName;

    @Column(length = 300)
    private String uniformImage;

    // 유니폼 수정 메서드
    public void uniformUpdate(String uniformName, String uniformImage, BrandEntity brand, ClubEntity club, int price) {
        this.uniformName = uniformName;
        this.uniformImage = uniformImage;
        this.brand = brand;
        this.club = club;
        this.price = price;
    }
}
