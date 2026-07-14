package com.shoppingmall.ecommercebackend.domain.user.service;

import com.shoppingmall.ecommercebackend.domain.user.dto.request.SignUpRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.response.SignUpResponse;
import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import com.shoppingmall.ecommercebackend.domain.user.exception.UserErrorCode;
import com.shoppingmall.ecommercebackend.domain.user.repository.UserRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        // 중복된 이메일인지 조회
        if(userRepository.existsByEmail(request.getEmail())){
            log.warn("[UserService] 중복된 이메일입니다: email={}", request.getEmail());
            throw new CustomException(UserErrorCode.USER_EMAIL_DUPLICATE);
        }

        // 중복된 전화번호인지 조회
        if(userRepository.existsByPhone(request.getPhone())){
            log.warn("[UserService] 중복된 전화번호입니다: phone={}", request.getPhone());
            throw new CustomException(UserErrorCode.USER_PHONE_DUPLICATE);
        }

        // 중복된 닉네임인지 조회
        if(userRepository.existsByNickname(request.getNickname())){
            log.warn("[UserService] 중복된 닉네임입니다: nickname={}", request.getNickname());
            throw new CustomException(UserErrorCode.USER_NICKNAME_DUPLICATE);
        }

        // 사용자 객체 생성
        UserEntity user =
                UserEntity.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .phone(request.getPhone())
                        .nickname(request.getNickname())
                        .build();

        // DB 저장
        UserEntity savedUser = userRepository.save(user);

        // 로그 출력
        log.info("[UserService] 회원가입 완료: email={}", user.getEmail());

        // 응답 세팅
        return SignUpResponse.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .nickname(savedUser.getNickname())
                .role(savedUser.getRole())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}
