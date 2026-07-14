package com.shoppingmall.ecommercebackend.domain.auth.service;

import com.shoppingmall.ecommercebackend.domain.auth.dto.request.LoginRequest;
import com.shoppingmall.ecommercebackend.domain.auth.dto.response.LoginResponse;
import com.shoppingmall.ecommercebackend.domain.auth.exception.AuthErrorCode;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import com.shoppingmall.ecommercebackend.global.security.CustomUserDetails;
import com.shoppingmall.ecommercebackend.global.security.CustomUserDetailsService;
import com.shoppingmall.ecommercebackend.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 로그인
    public LoginResponse login(LoginRequest request) {

        // 이메일 조회
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        // 비밀번호가 올바른지 조회
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            log.warn("[AuthService] 비밀번호가 올바르지 않습니다");
            throw new CustomException(AuthErrorCode.PASSWORD_FAIL);
        }

        // Access Token 발급
        String accessToken = jwtProvider.createAccessToken(userDetails);

        // 응답 반환
        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
