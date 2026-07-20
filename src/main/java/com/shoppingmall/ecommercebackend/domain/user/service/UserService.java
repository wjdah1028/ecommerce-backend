package com.shoppingmall.ecommercebackend.domain.user.service;

import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.request.SellerSignUpRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.request.SignUpRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.request.UpdatePasswordRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.request.UpdateUserRequest;
import com.shoppingmall.ecommercebackend.domain.user.dto.response.SignUpResponse;
import com.shoppingmall.ecommercebackend.domain.user.dto.response.UserInfoResponse;
import com.shoppingmall.ecommercebackend.domain.user.entity.Role;
import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import com.shoppingmall.ecommercebackend.domain.user.exception.UserErrorCode;
import com.shoppingmall.ecommercebackend.domain.user.repository.SocialAccountRepository;
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
    private final SocialAccountRepository socialAccountRepository;

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

    // 내 정보 조회
    public UserInfoResponse userInfo(Long userId) {

        // 사용자 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 응답 세팅
        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long userId) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // SocialEntity 삭제
        socialAccountRepository.deleteByUser(user);

        // User 삭제
        userRepository.deleteById(userId);

        // 회원 탈퇴 성공하면 로그 출력
        log.info("[UserService] 회원 삭제 성공: userId={}", userId);
    }

    @Transactional
    public void updateUserInfo(Long userId, UpdateUserRequest request) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 전과 같은 닉네임을 사용하는지 조회
        if (request.getNickname().equals(user.getNickname())) {
            log.warn("[UserService] 변경 전과 같은 닉네임입니다: nickname={}", user.getNickname());
            throw new CustomException(UserErrorCode.USER_NICKNAME_SAME);
        }

        // 닉네임이 중복되는지 조회
        if (userRepository.existsByNickname(request.getNickname())) {
            log.warn("[UserService] 중복된 닉네임입니다: nickname={}", request.getNickname());
            throw new CustomException(UserErrorCode.USER_NICKNAME_DUPLICATE);
        }

        // UserEntity 메서드 호출
        user.updateUserInfo(
                request.getName(),
                request.getNickname()
        );

        // 로그 출력
        log.info("[UserService] 사용자 정보 수정 성공: userId={}", userId);
    }

    // 비밀번호 수정
    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequest request) {

        // 사용자가 존재하는지 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        // 새로운 비밀번호 변경
        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));

        // 비밀번호 변경 성공 시 로그 출력
        log.info("[UserService] 비밀번호 변경 성공: userId={}", userId);
    }

    // 판매자 회원가입
    @Transactional
    public SignUpResponse sellerSignUp(SellerSignUpRequest request) {

        // 중복된 이메일인지 조회
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("[UseService] 중복된 이메일입니다: email={}", request.getEmail());
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
                        .role(Role.SELLER)
                        .build();

        // DB 저장
        UserEntity savedUser = userRepository.save(user);

        // 로그 출력
        log.info("[UserService] 판매자 회원가입 성공: userId={}", savedUser.getUserId());

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
