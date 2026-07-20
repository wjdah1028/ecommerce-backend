package com.shoppingmall.ecommercebackend.global.config;

import com.shoppingmall.ecommercebackend.domain.auth.service.OAuth2UserService;
import com.shoppingmall.ecommercebackend.global.security.CookieOAuth2AuthorizationRequestRepository;
import com.shoppingmall.ecommercebackend.global.security.JwtAuthenticationFilter;
import com.shoppingmall.ecommercebackend.global.security.OAuth2FailureHandler;
import com.shoppingmall.ecommercebackend.global.security.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CorsConfig에서 작성한 CORS 설정을 Spring Security에 연결
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // JWT 기반 API 서버에서는 CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // 서버에 로그인 상태를 저장하지 않는 Stateless 방식
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(HttpMethod.GET, "/logs")
                                        .permitAll()
                                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/users")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/auth/login")
                                        .permitAll()
                                        .requestMatchers("/oauth2/**", "/login/oauth2/**")
                                        .permitAll()
                                        .requestMatchers("/auth/callback", "/auth/error")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/sellers")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/leagues")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/brands")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint
                                .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository))
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
