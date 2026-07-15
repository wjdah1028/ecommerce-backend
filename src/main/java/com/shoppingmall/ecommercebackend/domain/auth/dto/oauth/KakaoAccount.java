package com.shoppingmall.ecommercebackend.domain.auth.dto.oauth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class KakaoAccount {

    private String nickname;

    public static KakaoAccount fromKakao(Map<String, Object> attributes) {

        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");

        return KakaoAccount.builder()
                .nickname((String) profile.get("nickname"))
                .build();
    }
}
