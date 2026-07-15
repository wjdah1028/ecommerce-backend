package com.shoppingmall.ecommercebackend.domain.user.entity;

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
@Table(name = "users")
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = true, length = 30)
    private String name;

    @Column(nullable = true, length = 200, unique = true)
    private String email;

    @Column(nullable = true, length = 200)
    private String password;

    @Column(nullable = true, length = 100, unique = true)
    private String phone;

    @Column(nullable = false, length = 100, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default // Builder로 만들게 되면 기본값을 설정해주는 어노테이션
    private Role role = Role.BUYER;
}
