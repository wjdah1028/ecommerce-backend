package com.shoppingmall.ecommercebackend.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${success-url}") // 프론트 연결후 URL 변경
    private String successUrl;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 사용자 정보 꺼내기
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        // 내부 회원 id 꺼내기
        Long userId = (Long) oAuth2User.getAttribute("userId");

        // JWT 발급
        String accessToken = jwtProvider.createAccessToken(userId);

        // 클라이언트에 전달 (redirect URL에 토큰 붙여서)
        String redirectUrl = successUrl + "?token=" + accessToken;
        response.sendRedirect(redirectUrl);
    }
}
