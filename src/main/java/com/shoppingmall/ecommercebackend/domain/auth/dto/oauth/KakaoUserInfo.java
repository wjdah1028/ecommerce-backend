package com.shoppingmall.ecommercebackend.domain.auth.dto.oauth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class KakaoUserInfo {

    private String id;
    private KakaoAccount kakaoAccount;

    public static KakaoUserInfo fromKakao(Map<String, Object> attributes) {
        return KakaoUserInfo.builder()
                .id(String.valueOf(attributes.get("id")))
                .kakaoAccount(KakaoAccount.fromKakao(
                        (Map<String, Object>) attributes.get("kakao_account")))
                .build();
    }
}
