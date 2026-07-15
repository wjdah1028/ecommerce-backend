package com.shoppingmall.ecommercebackend.domain.auth.service;

import com.shoppingmall.ecommercebackend.domain.auth.dto.oauth.KakaoUserInfo;
import com.shoppingmall.ecommercebackend.domain.user.entity.SocialAccountEntity;
import com.shoppingmall.ecommercebackend.domain.user.entity.UserEntity;
import com.shoppingmall.ecommercebackend.domain.user.repository.SocialAccountRepository;
import com.shoppingmall.ecommercebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final SocialAccountRepository socialAccountRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) {

        // 카카오에서 사용자 정보 받기
        OAuth2User oAuth2User = super.loadUser(request);

        // KakaoUserInfo로 파싱
        Map<String, Object> attributes = oAuth2User.getAttributes();
        KakaoUserInfo kakaoUserInfo;
        kakaoUserInfo = KakaoUserInfo.fromKakao(attributes);

        // DB에 기존 회원인지 확인
        String id = kakaoUserInfo.getId();
        Optional<SocialAccountEntity> socialAccountEntity = socialAccountRepository.findByLoginProviderAndSocialUserId("kakao", id);

        // 기존 회원이 아니면 UserEntity랑 SocialAccount에 저장, 기존 회원이면 연결된 유저 조회
        UserEntity user;
        if (socialAccountEntity.isEmpty()) {

            // 사용자 객체 생성
            UserEntity newUser = UserEntity.builder()
                    .nickname(kakaoUserInfo.getKakaoAccount().getNickname())
                    .build();

            // DB 저장
            user = userRepository.save(newUser);

            // 소셜 계정 객체 생성
            SocialAccountEntity socialAccount = SocialAccountEntity.builder()
                    .user(user)
                    .loginProvider("kakao")
                    .socialUserId(id)
                    .build();

            // DB 저장
            socialAccountRepository.save(socialAccount);
        } else {
            user = socialAccountEntity.get().getUser();
        }

        // 내부 회원 id를 attribute에 추가해서 이후 JWT 발급에 사용
        Map<String, Object> customAttributes = new HashMap<>(attributes);
        customAttributes.put("userId", user.getUserId());

        // OAuth2User 반환
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), customAttributes, "userId");
    }
}
