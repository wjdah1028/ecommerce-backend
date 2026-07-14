package com.shoppingmall.ecommercebackend.global.security;

import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    // User 엔티티를 Spring Security에서 사용할 수 있도록 함
    private final UserEntity user;

    public Long getUserId() {
        return user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 사용자 식별값 반환: email을 username(UserDetails의 사용자 식별 메서드 이름)처럼 사용
    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
