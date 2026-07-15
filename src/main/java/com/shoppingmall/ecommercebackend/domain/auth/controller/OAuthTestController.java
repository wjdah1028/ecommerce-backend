package com.shoppingmall.ecommercebackend.domain.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 프론트엔드 연동 전 소셜 로그인 리다이렉트 결과를 눈으로 확인하기 위한 임시 테스트용 컨트롤러
@Slf4j
@RestController
public class OAuthTestController {

    @GetMapping("/auth/callback")
    public String callback(@RequestParam String token) {
        log.info("소셜 로그인 성공, 발급된 토큰: {}", token);
        return "로그인 성공! 발급된 토큰: " + token;
    }

    @GetMapping("/auth/error")
    public String error() {
        log.info("소셜 로그인 실패");
        return "로그인 실패";
    }
}
